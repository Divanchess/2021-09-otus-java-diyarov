package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private final Deque<Memento> stack = new ArrayDeque<>();

    @Override
    public void onUpdated(Message msg) {
        stack.push(new Memento(new State(msg)));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
//        return stack.contains(new Message(id));
        throw new UnsupportedOperationException();
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
                    "message=" + (message == null ? null : message.toBuilder().build()) +
                    '}';
        }
    }

    record Memento(State state) {
        Memento(State state) {
            this.state = new State(state);
        }
    }

}
