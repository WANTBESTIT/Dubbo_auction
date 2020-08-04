package org.web.auction.mapper;

import org.apache.ibatis.annotations.Param;
import org.web.auction.pojo.Auctionrecord;
import org.web.auction.pojo.AuctionrecordExample;

import java.util.List;

public interface AuctionrecordMapper {
    long countByExample(AuctionrecordExample example);

    int deleteByExample(AuctionrecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auctionrecord record);

    int insertSelective(Auctionrecord record);

    List<Auctionrecord> selectByExample(AuctionrecordExample example);

    Auctionrecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auctionrecord record, @Param("example") AuctionrecordExample example);

    int updateByExample(@Param("record") Auctionrecord record, @Param("example") AuctionrecordExample example);

    int updateByPrimaryKeySelective(Auctionrecord record);

    int updateByPrimaryKey(Auctionrecord record);
}