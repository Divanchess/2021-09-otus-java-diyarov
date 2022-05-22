package ru.otus.atm.banknote;

public abstract class Banknote {
    private static final int VALUE = 0;
    public Banknote() {}
    public abstract int getValue();

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

    public static class One extends Banknote {
        private static final int VALUE = 1;

        @Override
        public int getValue() {
            return VALUE;
        }
    }

    public static class Ten extends Banknote {
        private static final int VALUE = 10;

        @Override
        public int getValue() {
            return VALUE;
        }
    }

    public static class Fifty extends Banknote {
        private static final int VALUE = 50;

        @Override
        public int getValue() {
            return VALUE;
        }
    }

    public static class OneHundred extends Banknote {
        private static final int VALUE = 100;

        @Override
        public int getValue() {
            return VALUE;
        }
    }

    public static class FiveHundred extends Banknote {
        private static final int VALUE = 500;

        @Override
        public int getValue() {
            return VALUE;
        }
    }

    public static class OneThousand extends Banknote {
        private static final int VALUE = 1000;

        @Override
        public int getValue() {
            return VALUE;
        }
    }

    public static class FiveThousand extends Banknote {
        private static final int VALUE = 5000;

        @Override
        public int getValue() {
            return VALUE;
        }
    }
}

