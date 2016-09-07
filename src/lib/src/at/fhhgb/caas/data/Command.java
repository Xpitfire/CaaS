package at.fhhgb.caas.data;

import java.io.Serializable;

/**
 * Created by Dinu Marius-Constantin on 17.05.2015.
 */
public class Command<T> implements Serializable, Comparable<Command> {

    private static final long serialVersionUID = -2862919844239975961L;

    public enum Operation implements Serializable {
        GET,
        GET_ALL,
        CONTAINS,
        INSERT,
        MODIFY,
        DELETE
        ;
        private static final long serialVersionUID = -2324286359239975961L;
    }

    public enum ObjectType implements Serializable {
        PERSON,
        CATEGORY,
        MEAL,
        ORDER
        ;
        private static final long serialVersionUID = -2242863234239972221L;
    }

    private Operation operation;
    private ObjectType objectType;
    private T data;

    public Command() {
    }

    public Command(Operation operation, ObjectType objectType, T data) {
        this.operation = operation;
        this.objectType = objectType;
        this.data = data;
    }

    public Command(Operation operation, T data) {
        this(operation, null, data);
    }

    public Command(Operation operation, ObjectType objectType) {
        this(operation, objectType, null);
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Command<?> command = (Command<?>) o;

        if (operation != command.operation) return false;
        if (objectType != command.objectType) return false;
        if (data != null ? !data.equals(command.data) : command.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = operation.hashCode();
        result = 31 * result + (objectType != null ? objectType.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Command o) {
        return operation.compareTo(o.operation);
    }

    @Override
    public String toString() {
        return String.format("Command{operation=%s, data=%s}", operation, data);
    }
}
