package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.core.Image;
import models.core.Image.Platform;
import models.core.Image.Resolution;
import models.core.OrderedEntity;

import org.apache.commons.logging.LogFactory;

import play.db.DB;

@Entity
@Table(name="ida_banners")
public class Banner extends OrderedEntity{

    {
        ENTITY_NAME = "banners";
    }

    @Column(name="title")
    public String title;

    @Column(name="url")
    public String url;

    @Column(name="text")
    public String text;

    @Column(name="image_id")
    public String imageId;

    public Image getImage(Platform platform, Resolution resolution) {
        return Image.findExact(imageId, platform, resolution);
    }

    public Image getPreview() {
        return Image.find("byImageId", imageId).first();
    }

    public static long getLastOrderNumber() {
        ResultSet rs = DB.executeQuery("select max(order_number) from IDA_BANNERS");
        if (rs == null) {
            return 0;
        } else {
            try {
                rs.next();
                return rs.getLong(1);
            } catch (SQLException e) {
                LogFactory.getLog(Banner.class).error("While fetching max order_number", e);
                return 0;
            }
        }
    }

    @Override
    protected OrderedEntity findSelf() {
        return Banner.findById(id);
    }

    @Override
    protected OrderedEntity findPrevious(OrderedEntity self) {
        return Banner.find("select b from Banner b where b.order <= ? and b.id <> ? order by b.order desc", self.order, self.id).first();
    }

    @Override
    protected OrderedEntity findSuccessive(OrderedEntity self) {
        return Banner.find("select b from Banner b where b.order >= ? and b.id <> ? order by b.order desc", self.order, self.id).first();
    }
}
