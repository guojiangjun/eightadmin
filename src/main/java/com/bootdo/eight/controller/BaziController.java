package com.bootdo.eight.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.*;
import com.bootdo.common.utils.excel.BaziExcelVo;
import com.bootdo.common.utils.excel.ExcelUtil2;
import com.bootdo.common.utils.excel.ExcelUtils;
import com.bootdo.eight.domain.BaziData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bootdo.eight.domain.BaziDO;
import com.bootdo.eight.service.BaziService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 16:52:01
 */

@Controller
@RequestMapping("/eight/bazi")
public class BaziController {
    private static final Logger log = LoggerFactory.getLogger(BaziController.class);
    private static Random random = new Random();
    @Autowired
    private BaziService baziService;
    ApplicationContext applicationContext;

    @GetMapping()
    @RequiresPermissions("eight:bazi:bazi")
    String Bazi(String userid, Model model) {
        model.addAttribute("userid", userid);
        return "eight/bazi/bazi";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("eight:bazi:bazi")
    public PageUtils list(@RequestParam Map<String, Object> params) throws ParseException {
        //查询列表数据
        params.put("sort", "createTime");
        params.put("order", "desc");
        Query query = new Query(params);
        List<BaziExcelVo> baziList = baziService.BaziExcelVoList(query);
        int no = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
        for (BaziExcelVo baziExcelVo : baziList) {
            //处理阴历日期格式
            String d = baziExcelVo.getSolardatetime();
            String d2 = dateFormat2.format(dateFormat.parse(baziExcelVo.getSolardatetime()));
            Calendar today = Calendar.getInstance();
            today.setTime(LunarUtil.chineseDateFormat.parse(d));
            LunarUtil lunar = new LunarUtil(today);
            baziExcelVo.setLunardatetime(lunar + " "+d2);
            baziExcelVo.setNo(no);
            baziExcelVo.setMale(baziExcelVo.getMale().equals("0")?"男":"女");
            no +=1;
            JSONObject jsonObject = JSON.parseObject(baziExcelVo.getBaziConfig());
            if(ObjectUtil.isNotEmpty(jsonObject)){
                baziExcelVo.setBirthCity(jsonObject.getString("birthCity"));
                baziExcelVo.setDateTime(jsonObject.getString("dateTime"));
                baziExcelVo.setNormalTaiyang(jsonObject.getString("normalTaiyang"));
                baziExcelVo.setJingdu(jsonObject.getString("jingdu"));
                baziExcelVo.setRealTaiyang(jsonObject.getString("realTaiyang"));
                baziExcelVo.setTaiyang(jsonObject.getBoolean("isTaiyang")?"yes":"no");
                baziExcelVo.setType(jsonObject.getString("type"));
                baziExcelVo.setWeidu(jsonObject.getString("weidu"));
                baziExcelVo.setZoneJingdu(jsonObject.getString("zoneJingdu"));
                baziExcelVo.setXialingshi(jsonObject.getBoolean("isXialingshi")?"yes":"no");
            }

        }
        int total = baziService.count(query);
        PageUtils pageUtils = new PageUtils(baziList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("eight:bazi:add")
    String add() {
        return "eight/bazi/add";
    }

    @GetMapping("/edit/{baziid}")
    @RequiresPermissions("eight:bazi:edit")
    String edit(@PathVariable("baziid") String baziid, Model model) {
        BaziDO bazi = baziService.get(baziid);
        model.addAttribute("bazi", bazi);
        return "eight/bazi/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("eight:bazi:add")
    public R save(BaziDO bazi) {
        if (baziService.save(bazi) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("eight:bazi:edit")
    public R update(BaziDO bazi) {
        baziService.update(bazi);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("eight:bazi:remove")
    public R remove(String baziid) {
        if (baziService.remove(baziid) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("eight:bazi:batchRemove")
    public R remove(@RequestParam("ids[]") String[] baziids) {
        baziService.batchRemove(baziids);
        return R.ok();
    }

    @PostMapping(value = "/uploadExcel", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "用户信息Excel导入数据", notes = "用户信息Excel导入数据", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "上传成功！"),
            @ApiResponse(code = 500, message = "上传失败！")
    })
    public R uploadExcel(@ApiParam(value = "用户信息Excel导入数据", required = true) MultipartFile file) throws Exception {
        //ResultEntity result = new ResultEntity();
        try {


            List<List<String>> lists = ExcelUtil2.readExcel(file.getInputStream());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
            for (int i = 1; i < lists.size(); i++) {
                //for (int j = 0; j < lists.get(i).size(); j++) {
                List<String> stringList = lists.get(i);
                if (StringUtils.isEmpty(stringList.get(2))) {
                    break;
                }
                String baziId;
                String md5Str = MD5Util.computeMd5(stringList.get(0) + stringList.get(5) + LocalDateTime.now());
                BaziDO baziDO = new BaziDO();
                String d  =  stringList.get(6).replaceAll("年","-").replaceAll("月","-")+":00";
                baziDO.setBaziname(stringList.get(3));
                baziDO.setSolardatetime(formatter.parse(d));
                String d2 = dateFormat2.format(formatter.parse(d));
                Calendar today = Calendar.getInstance();
                today.setTime(LunarUtil.chineseDateFormat.parse(d));

                LunarUtil lunar = new LunarUtil(today);
                String Lunardatetime = lunar.year+"-"+lunar.month+"-"+lunar.day +" " + d2;

                String a = formatter.format(formatter.parse(Lunardatetime));
                baziDO.setLunardatetime(a);//处理
                baziDO.setUserid(Integer.valueOf(stringList.get(0)));
                baziDO.setRemark(stringList.get(14));
                baziDO.setMale(stringList.get(4).equals("男") ? 1 : 0);
                //处理config
                HashMap<String, Object> paramMap = new HashMap<>();
                paramMap.put("birthTime", d);
                paramMap.put("male", baziDO.getMale());

                String result3 = HttpUtil.get("http://47.103.214.98:8089/eightapp/bazi/adminByBaziData", paramMap);

                baziDO.setBazidata(JSON.parseObject(result3).getString("data"));
                String isTaiyang = "";
                String isXialingshi ="";
                String type ="";
                if(StringUtils.isNotEmpty(stringList.get(7)) && stringList.get(7).trim().equals("Yes")){
                    isTaiyang = "true";
                }else if(StringUtils.isNotEmpty(stringList.get(7)) && stringList.get(7).trim().equals("No")){
                    isTaiyang = "false";
                }else{
                    return R.error("导入失败,真太阳时校正输入有误");
                }
                if(StringUtils.isNotEmpty(stringList.get(8)) && stringList.get(8).trim().equals("Yes")){
                    isXialingshi = "true";
                }else if(StringUtils.isNotEmpty(stringList.get(8)) && stringList.get(8).trim().equals("No")){
                    isXialingshi = "false";
                }else{
                    return R.error("导入失败,夏令时校正输入有误");
                }
                if(StringUtils.isNotEmpty(stringList.get(5)) && stringList.get(5).trim().equals("公历")){
                    type = "0";
                }else if(StringUtils.isNotEmpty(stringList.get(5)) && stringList.get(5).trim().equals("农历")){
                    type = "0";
                }else{
                    return R.error("导入失败,是否公历农历输入有误");
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", type);
                jsonObject.put("dateTime", stringList.get(9).replaceAll("年","-").replaceAll("月","-"));
                jsonObject.put("birthCity", stringList.get(11));
                jsonObject.put("zoneJingdu", stringList.get(15));
                jsonObject.put("isTaiyang", isTaiyang);
                jsonObject.put("normalTaiyang", stringList.get(13));
                jsonObject.put("realTaiyang", stringList.get(12));
                jsonObject.put("jingdu", stringList.get(16));
                jsonObject.put("weidu", stringList.get(17));
                jsonObject.put("isXialingshi", isXialingshi);
                baziDO.setBaziconfig(jsonObject.toJSONString());

                baziDO.setCreatetime(new Date());
                baziDO.setUpdatetime(new Date());
                if (!stringList.get(2).equals("0")) {
                    baziId = stringList.get(2);
                    baziDO.setBaziid(baziId);
                    baziService.update(baziDO);
                } else {
                    baziId = random.nextInt(10) + md5Str.substring(md5Str.length() - 4) + Integer.valueOf(stringList.get(0));
                    baziDO.setBaziid(baziId);
                    baziService.save(baziDO);

                }
            }
            log.info("长度：" + lists.size());
            //TODO 入库的代码自行补充
            return R.ok("导入成功");
        } catch (Exception e) {
            return R.error("导入失败");
        }
    }
    /**
     * 下载模板
     *
     * @param response
     * @param request
     */
    /**
     * 下载模板
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "downloadTemp")
    public void downloadTemp(HttpServletResponse response, HttpServletRequest request) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String filename = "导入模板.xls";
            String path = "excleTemplate.xlsx";
            Resource resource = applicationContext.getResource("classpath:" + path);
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping(value = {"/downloadConfigFilter"}, method = {RequestMethod.GET})
    public void download(String userId, HttpServletResponse response) throws IOException, ParseException {
        if(StringUtils.isEmpty(userId)){
          return;
        }
        Map<String, Object> params = new HashMap<>();
       // params.put("offset", 0);
       // params.put("limit", 10);
        params.put("userid", userId);
        List<BaziExcelVo> baziList = baziService.BaziExcelVoList(params);
        int no = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy年MM月dd HH:mm");
        SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        for (BaziExcelVo baziExcelVo : baziList) {
            //处理阴历日期格式
            String d = baziExcelVo.getSolardatetime();
            String d2 = dateFormat2.format(dateFormat.parse(baziExcelVo.getSolardatetime()));
            Calendar today = Calendar.getInstance();
            today.setTime(LunarUtil.chineseDateFormat.parse(d));
            LunarUtil lunar = new LunarUtil(today);
            baziExcelVo.setLunardatetime(lunar + " "+d2);
            baziExcelVo.setNo(no);
            baziExcelVo.setMale(baziExcelVo.getMale().equals("0")?"男":"女");
            no +=1;
            baziExcelVo.setSolardatetime(dateFormat3.format(dateFormat.parse(baziExcelVo.getSolardatetime())));
            JSONObject jsonObject = JSON.parseObject(baziExcelVo.getBaziConfig());
            if(ObjectUtil.isNotEmpty(jsonObject)){
                baziExcelVo.setBirthCity(jsonObject.getString("birthCity"));
                baziExcelVo.setDateTime(dateFormat4.format(dateFormat.parse(jsonObject.getString("dateTime"))));
                baziExcelVo.setNormalTaiyang(StringUtils.isNotEmpty(jsonObject.getString("normalTaiyang"))?dateFormat4.format(dateFormat.parse(jsonObject.getString("normalTaiyang"))):"");
                baziExcelVo.setJingdu(jsonObject.getString("jingdu"));
                baziExcelVo.setRealTaiyang(StringUtils.isNotEmpty(jsonObject.getString("realTaiyang"))?dateFormat4.format(dateFormat.parse(jsonObject.getString("realTaiyang"))):"");
                baziExcelVo.setTaiyang(jsonObject.getBoolean("isTaiyang")?"Yes":"No");
                baziExcelVo.setType(jsonObject.getString("type").equals("0")?"公历":"农历");
                baziExcelVo.setWeidu(jsonObject.getString("weidu"));
                baziExcelVo.setZoneJingdu(jsonObject.getString("zoneJingdu"));
                baziExcelVo.setXialingshi(jsonObject.getBoolean("isXialingshi")?"Yes":"No");
            }

        }
        long t1 = System.currentTimeMillis();
        ExcelUtils.writeExcel(response, baziList, BaziExcelVo.class);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }
}
