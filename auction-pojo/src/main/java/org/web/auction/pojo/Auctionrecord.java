package org.web.auction.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 拍卖记录
 */
public class Auctionrecord implements Serializable {
    private Integer id;

    private Integer userid;

    private Integer auctionid;

    private Date auctiontime;

    private Double auctionprice;

    private User user;  //一方

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(Integer auctionid) {
        this.auctionid = auctionid;
    }

    public Date getAuctiontime() {
        return auctiontime;
    }

    public void setAuctiontime(Date auctiontime) {
        this.auctiontime = auctiontime;
    }

    public Double getAuctionprice() {
        return auctionprice;
    }

    public void setAuctionprice(Double auctionprice) {
        this.auctionprice = auctionprice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userid=").append(userid);
        sb.append(", auctionid=").append(auctionid);
        sb.append(", auctiontime=").append(auctiontime);
        sb.append(", auctionprice=").append(auctionprice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}