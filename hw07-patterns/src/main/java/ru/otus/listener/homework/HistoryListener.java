package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {
    private final HashMap<Long, Deque<Memento>> hash_stack = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        State state = new State(msg);
        long id = msg.getId();
        if (!hash_stack.containsKey(id)) {
            hash_stack.put(id, new ArrayDeque<>( List.of(new Memento(state)) ));
        } else {
            hash_stack.get(id).push(new Memento(state));
        }
        System.out.println(hash_stack);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        if (hash_stack.containsKey(id) ) {
            return Optional.ofNullable(hash_stack.get(id).getLast().state().getMessage());
        }
        return Optional.empty();
    }

    static class State {
        private final Message message;

        State(Message message) {
            this.message = message;
        }

        State(State state) {
            this.message = state.message.toBuilder().build();
        }

        Message getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "State{" +
                    "message=" + (message == null ? null : message) +
                    '}';
        }
    }

    record Memento(State state) {
        Memento(State state) {
            this.state = new State(state);
        }
    }

}
