package org.web.auction.service;

import org.web.auction.pojo.Auction;
import org.web.auction.pojo.Auctionrecord;
import org.web.auction.pojo.Result;

import java.util.List;

public interface AuctionService {

    public List<Auction> queryAuctions();

    // 模糊查询
    public List<Auction> queryAuctions(Auction auction);

    // 显示分页
    public Result queryAuctions(int pageNum, int pageSize);

    public void addAuction(Auction auction);

    public Auction findAuctionAndAuctionRecordList(int id);

    public void addAuctionRecord(Auctionrecord record) throws Exception;

    public void updateAuction(Auction auction);

    public void removeAuction(int aucitonid);
}
