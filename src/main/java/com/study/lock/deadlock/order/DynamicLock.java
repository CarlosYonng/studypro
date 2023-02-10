package com.study.lock.deadlock.order;

import lombok.Data;



public class DynamicLock {
    @Data
    class Account {

        private Double money;

        public void debit(Double amount) {
            this.money -= amount;
        }

        public void credit(Double amount) {
            this.money += amount;
        }
    }

    public void transferMoney(Account fromAccount, Account toAccount, double amount) {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.money - amount < 0) {
                    throw new IllegalStateException();
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }
    }

    private static final Object tieLock = new Object();

    public void transferMoney2(final Account fromAccount, final Account toAccount, final double amount) {
        class Helper {
            public void transfer() {
                if (fromAccount.money - amount < 0) {
                    throw new IllegalStateException();
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }

        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);
        if (fromHash < toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    new Helper().transfer();
                }
            }
        } else if (toHash < fromHash) {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }
}
