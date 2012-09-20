package models.core;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import controllers.CRUD.Hidden;

@MappedSuperclass
public abstract class OrderedEntity extends UpdateTimeAwareModel {

    @Hidden
    @Column(name="order_number")
    public Long order;

    protected abstract OrderedEntity findSelf();
    protected abstract OrderedEntity findPrevious(OrderedEntity self);
    protected abstract OrderedEntity findSuccessive(OrderedEntity self);

    protected void swapEntities(OrderedEntity first, OrderedEntity second) {
        if (first == null || second == null) {
            return;
        }

        long tmp = second.order;
        second.order = first.order;
        first.order = tmp;

        first.save();
        second.save();

    }

    public void moveUp() {
        OrderedEntity self = findSelf();
        swapEntities(self, findPrevious(self));
    }

    public void moveDown() {
        OrderedEntity self = findSelf();
        swapEntities(self, findSuccessive(self));
    }
}
