package org.web.auction.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.web.auction.mapper.AuctionCustomMapper;
import org.web.auction.mapper.AuctionMapper;
import org.web.auction.mapper.AuctionrecordMapper;
import org.web.auction.pojo.Auction;
import org.web.auction.pojo.AuctionExample;
import org.web.auction.pojo.Auctionrecord;
import org.web.auction.pojo.Result;
import org.web.auction.service.AuctionService;
import org.web.auction.utils.CustomException;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionMapper auctionMapper;
    @Autowired
    private AuctionCustomMapper auctionCustomMapper;
    @Autowired
    private AuctionrecordMapper auctionrecordMapper;


    @Override
    public List<Auction> queryAuctions() {
        return auctionMapper.selectByExample(null);
    }

    /**
     * 模糊查询
     * @param auction
     * @return
     */
    @Override
    public List<Auction> queryAuctions(Auction auction) {

        AuctionExample example = new AuctionExample();
        AuctionExample.Criteria criteria = example.createCriteria();
        if (auction != null) {
            // 1.商品名称 模糊
            if (auction.getAuctionname() != null && !"".equals(auction.getAuctionname())) {
                criteria.andAuctionnameLike("%" + auction.getAuctionname() + "%");
            }
            // 2.商品描述 模糊
            if (auction.getAuctiondesc() != null && !"".equals(auction.getAuctiondesc())) {
                criteria.andAuctiondescLike("%" + auction.getAuctiondesc() + "%");
            }
            // 3.起拍时间 >
            if (auction.getAuctionstarttime() != null) {
                criteria.andAuctionendtimeGreaterThan(auction.getAuctionstarttime());
            }
            // 4.结束时间 <
            if (auction.getAuctionendtime() != null) {
                criteria.andAuctionendtimeLessThan(auction.getAuctionendtime());
            }
            // 5.起拍价 >
            if (auction.getAuctionstartprice() != null) {
                criteria.andAuctionstartpriceGreaterThan(auction.getAuctionstartprice());
            }
        }
        // 按开始日期降序排列
        example.setOrderByClause("auctionstarttime desc");
        List<Auction> list = auctionMapper.selectByExample(example);

        return list;
    }

    @Override
    public Result queryAuctions(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Auction> list = auctionMapper.selectByExample(null);
        PageInfo pageInfo = new PageInfo(list);
        Result result = new Result();
        result.setData(list);
        result.setPageInfo(pageInfo);
        return result;
    }

    @Override
    public void addAuction(Auction auction) {
        auctionMapper.insert(auction);
    }

    @Override
    public Auction findAuctionAndAuctionRecordList(int id) {
        return auctionCustomMapper.findAuctionAndAuctionRecordList(id);
    }

    /**
     * 1. 判断竞拍时间
     * 2. 如果当前商品已经竞拍， 竞拍价格必须高于当前的最高竞价
     * 3. 如果当前商品没有竞拍记录，竞拍价格必须高于起拍价
     *
     * @param record
     * @throws Exception
     */
    @Override  //使用异常处理器
    public void addAuctionRecord(Auctionrecord record) throws Exception {
        //先查询商品的详情(使用映射的方法来封装: List<AuctionRecord>)
        //auctionMapper.selectByPrimaryKey(record.getAuctionid());
        Auction auction = auctionCustomMapper.findAuctionAndAuctionRecordList(record.getAuctionid());
        //1. 判断竞拍时间
        if (auction.getAuctionendtime().after(new Date()) == false) {
            throw new CustomException("该商品拍卖时间已经结束");
        }
        //判断是否有竞拍记录
        if (auction.getAuctionrecordList() != null && auction.getAuctionrecordList().size() > 0) {
            //3. 如果当前商品已经竞拍， 竞拍价格必须高于当前的最高竞价
            //集合的第一条记录就是最高竞价记录（排序后）
            Auctionrecord maxRecord = auction.getAuctionrecordList().get(0);
            if (record.getAuctionprice() <= maxRecord.getAuctionprice()) {
                throw new CustomException("竞拍价格必须高于当前的最高竞价");
            }

        } else { //第一次竞价: 2. 如果当前商品没有竞拍记录，竞拍价格必须高于起拍价
            if (record.getAuctionprice() <= auction.getAuctionstartprice()) {
                throw new CustomException("竞拍价格必须高于起拍价");
            }
        }
        auctionrecordMapper.insert(record);
    }

    @Override
    public void updateAuction(Auction auction) {
        auctionMapper.updateByPrimaryKey(auction);
    }

    @Override
    public void removeAuction(int aucitonid) {
        AuctionExample example = new AuctionExample();
        AuctionExample.Criteria criteria = example.createCriteria();
        criteria.andAuctionidEqualTo(aucitonid);
        auctionMapper.deleteByExample(example);
        auctionMapper.deleteByPrimaryKey(aucitonid);
    }
}
