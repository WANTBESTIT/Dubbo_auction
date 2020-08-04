package org.web.auction.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.web.auction.pojo.Auction;
import org.web.auction.pojo.Auctionrecord;
import org.web.auction.pojo.Result;
import org.web.auction.pojo.User;
import org.web.auction.service.AuctionService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class AuctionController {

    @Reference
    private AuctionService auctionService;

    public static final int PAGE_SIZE = 6;

    @RequestMapping("/queryAuctions")
    public ModelAndView queryAuctions(@ModelAttribute("condition") Auction auction,
                                      @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo) {
        ModelAndView mv = new ModelAndView();

        Result result = auctionService.queryAuctions(pageNo, PAGE_SIZE);
        List<Auction> list = auctionService.queryAuctions(auction);
        mv.addObject("auctionList", list);
        mv.addObject("auctionList", result.getData());
        mv.addObject("pageInfo", result.getPageInfo());

        //获取shiro上下文中的用户的身份对象
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        mv.addObject("user", user);

        mv.setViewName("index");
        return mv;

    }

    @RequestMapping("/publishAuctions")
    public String publishAuctions(Auction auction, MultipartFile pic) {
        try {
            //1.文件上传
            //先判断是否有文件数据, pic不为null
            if (pic.getSize() > 0) {
                //把文件保存到tomcat目录之下, 先要获取文件保存的绝对路径
                String path = "d:/pictures";

                String filename = pic.getOriginalFilename();
                File targetFile = new File(path, filename);
                pic.transferTo(targetFile);

                //设置图片的参数
                auction.setAuctionpic(filename);
                auction.setAuctionpictype(pic.getContentType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2.添加商品数据
        auctionService.addAuction(auction);
        return "redirect:/queryAuctions";
    }

    @GetMapping("/toAdd")
    public String toAdd() {
        return "addAuction";
    }

    //restful风格
    @RequestMapping("/toDetail/{id}")
    public ModelAndView toDetail(@PathVariable int id) {
        ModelAndView mv = new ModelAndView();
        Auction auctionDetail = auctionService.findAuctionAndAuctionRecordList(id);
        mv.addObject("auctionDetail", auctionDetail);
        mv.setViewName("auctionDetail");
        return mv;
    }

    // mapper --> service --> handler-->DispatcherServlet--> 异常处理器
    @RequestMapping("/saveAuctionRecord")
    public String saveAuctionRecord(Auctionrecord record, HttpSession session) throws Exception {
        record.setAuctiontime(new Date());
        //User loginUser = (User) session.getAttribute("user");
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        record.setUserid(loginUser.getUserid());
        auctionService.addAuctionRecord(record);
        return "redirect:/toDetail/" + record.getAuctionid();
    }

    /**
     * 进入修改页面
     * @param auctionid
     * @return
     */
    @RequestMapping("/toUpdateAuction/{auctionid}")
    public ModelAndView toUpdateAuction(@PathVariable("auctionid") int auctionid) {
        ModelAndView mv = new ModelAndView();
        Auction auction = auctionService.findAuctionAndAuctionRecordList(auctionid);
        mv.addObject("auction", auction);
        mv.setViewName("updateAuction");
        return mv;
    }

    /**
     * 提交修改信息
     *
     * @return
     */
    @RequestMapping("/submitUpdateAuction")
    public String submitUpdateAuction(Auction auction, MultipartFile pic) {
        try {
            if (pic.getSize() > 0) {
                // 1.另存二进制文件，保存到pic目录下的Tomcat绝对路径
                String path = "d:/pictures";
                System.out.println(path);
                File oldFile = new File(path, auction.getAuctionpic());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
                String filename = pic.getOriginalFilename();
                File targetFile = new File(path, filename);
                // 把二进制文件移到目标文件位置
                pic.transferTo(targetFile);

                // 设置图片的文件路径和文件类型
                auction.setAuctionpic(pic.getOriginalFilename());
                auction.setAuctionpictype(pic.getContentType());
             }
            } catch(Exception e){
                e.printStackTrace();
                return "addAuction";
            }

        auctionService.updateAuction(auction);
        return "redirect:/queryAuctions";
    }

    @RequestMapping("/toDeleteAuction/{auctionid}")
    public String toDeleteAuction(@PathVariable  int auctionid) {
        auctionService.removeAuction(auctionid);
        return "redirect:/queryAuctions";
    }
}
