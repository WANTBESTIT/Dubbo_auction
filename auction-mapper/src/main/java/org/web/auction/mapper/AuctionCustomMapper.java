package org.web.auction.mapper;

import org.web.auction.pojo.Auction;

public interface AuctionCustomMapper {

    public Auction findAuctionAndAuctionRecordList(int id);
}
