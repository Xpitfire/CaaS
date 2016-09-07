package at.fhhgb.caas.server.socket;

import at.fhhgb.caas.data.*;
import at.fhhgb.caas.model.dao.CategoryDAO;
import at.fhhgb.caas.model.dao.MealDAO;
import at.fhhgb.caas.model.dao.OrderDAO;
import at.fhhgb.caas.model.dao.PersonDAO;
import at.fhhgb.caas.server.CaaSServerApp;
import at.fhhgb.caas.server.model.DAOFactory;
import at.fhhgb.caas.util.ThreadUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class ClientHandler implements Runnable, AutoCloseable {

    private Logger logger = Logger.getLogger(CaaSServerApp.SERVER_LOGGER);

    private static PersonDAO personDAO;
    private static MealDAO mealDAO;
    private static OrderDAO orderDAO;
    private static CategoryDAO categoryDAO;

    private boolean keepAlive;
    private long clientId;
    private long ttlTimer;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ClientHandler(long clientId, Socket socket) {
        this.clientId = clientId;
        this.socket = socket;
        keepAlive = true;
        personDAO = DAOFactory.getPersonDAO();
        mealDAO = DAOFactory.getMealDAO();
        orderDAO = DAOFactory.getOrderDAO();
        categoryDAO = DAOFactory.getCategoryDAO();
        ttlTimer = System.currentTimeMillis() + ClientListener.TTL;
    }

    public boolean isAlive() {
        return keepAlive;
    }

    public long getClientId() {
        return clientId;
    }

    public long getTTLTimer() {
        return ttlTimer;
    }

    public void resetTTLTimer() {
        ttlTimer = System.currentTimeMillis() + ClientListener.TTL;
    }

    public synchronized void writeObject(Object... objects) {
        for (Object o : objects) {
            try {
                outputStream.writeObject(o);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            new Thread(new ClientReadHandler()).start();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    @Override
    public void close() {
        keepAlive = false;
        try {
            if (outputStream != null)
                outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (inputStream != null)
                inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientReadHandler implements Runnable {

        private void get(Command command) throws Exception {
            Object data = command.getData();
            if (data instanceof Person) {
                Person person = (Person) data;
                person = personDAO.getPerson(person.getUsername());
                Command cmdRsp = new Command(Command.Operation.GET, person);
                writeObject(cmdRsp);
            }
        }

        private void getAll(Command command) throws Exception {
            Object data = command.getObjectType();
            if (data == Command.ObjectType.PERSON) {
                List<Person> list = personDAO.getPersons().stream().collect(Collectors.toList());
                Command cmdRsp = new Command(Command.Operation.GET_ALL, list);
                writeObject(cmdRsp);
            } else if (data == Command.ObjectType.CATEGORY) {
                List<Category> list = categoryDAO.getCategories().stream().collect(Collectors.toList());
                Command cmdRsp = new Command(Command.Operation.GET_ALL, list);
                writeObject(cmdRsp);
            } else if (data == Command.ObjectType.MEAL) {
                List<Meal> list = mealDAO.getMeals().stream().collect(Collectors.toList());
                Command cmdRsp = new Command(Command.Operation.GET_ALL, list);
                writeObject(cmdRsp);
            } else if (data == Command.ObjectType.ORDER) {
                List<Order> list = orderDAO.getOrders().stream().collect(Collectors.toList());
                Command cmdRsp = new Command(Command.Operation.GET_ALL, list);
                writeObject(cmdRsp);
            }
        }

        private void modify(Command command) throws Exception {
            Object data = command.getData();
            if (data instanceof Person) {
                personDAO.modifyPerson((Person) data);
            } else if (data instanceof Order) {
                orderDAO.modifyOrder((Order) data);
            } else if (data instanceof Meal) {
                mealDAO.modifyMeal((Meal) data);
            } else if (data instanceof Category) {
                categoryDAO.modifyCategory((Category) data);
            }
        }

        public void insert(Command command) throws Exception {
            Object data = command.getData();
            if (data instanceof Person) {
                personDAO.addPerson((Person) data);
            } else if (data instanceof Category) {
                categoryDAO.addCategory((Category) data);
            } else if (data instanceof Meal) {
                mealDAO.addMeal((Meal) data);
            } else if (data instanceof Order) {
                orderDAO.addOrder((Order) data);
            }
        }

        public void delete(Command command) throws Exception {
            Object data = command.getData();
            if (data instanceof Person) {
                personDAO.removePerson((Person) data);
            } else if (data instanceof Category) {
                categoryDAO.removeCategory((Category) data);
            } else if (data instanceof Meal) {
                mealDAO.removeMeal((Meal) data);
            } else if (data instanceof Order) {
                orderDAO.removeOrder((Order) data);
            }
        }

        private void contains(Command command) throws Exception {
            Object data = command.getData();
            if (data instanceof Person) {
                Person person = (Person) data;
                person = personDAO.getPerson(person.getUsername());
                Command cmdRsp = new Command(Command.Operation.CONTAINS, person);
                writeObject(cmdRsp);
            }
        }

        @Override
        public void run() {
            while (keepAlive) {
                try {
                    Object o;
                    synchronized (this) {
                        o = inputStream.readObject();
                    }
                    if (o instanceof Command) {
                        Command cmd = (Command) o;
                        if (cmd.getOperation() == Command.Operation.GET)
                            get(cmd);
                        else if (cmd.getOperation() == Command.Operation.GET_ALL)
                            getAll(cmd);
                        else if (cmd.getOperation() == Command.Operation.INSERT)
                            insert(cmd);
                        else if (cmd.getOperation() == Command.Operation.DELETE)
                            delete(cmd);
                        else if (cmd.getOperation() == Command.Operation.MODIFY)
                            modify(cmd);
                        else if (cmd.getOperation() == Command.Operation.CONTAINS)
                            contains(cmd);
                        else
                            logger.log(Level.WARNING, "Command not supported: {0}", cmd.getOperation());
                        resetTTLTimer();
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, String.format("Lost client connection! ClientHandler ID: %s", clientId), e);
                    close();
                }
                ThreadUtil.sleep(ThreadUtil.SLEEP_200_MILLIS);
            }
        }

    }
}
