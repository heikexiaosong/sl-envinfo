package com.gavel.schedule;

import com.gavel.monitor.persistence.entity.EnvInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnvinfoWeb {

  private static final String CONTEXT_PATH = "http://113.140.66.227:8111/envinfo_web";

  private static final Logger LOG = LoggerFactory.getLogger(EnvinfoWeb.class);

  public static void main(String[] args) {

    try {
      login();

      // 废气监控
      System.out.println(loadYQData());

      // 污水监控
      System.out.println(loadWSData());

      // 污水处理厂监控
      System.out.println(loadFCData());

    } catch (Exception e) {
      LOG.error("[Task异常]" + e.getMessage());
    }



  }


  /**
   * 废气监控
   */
  public static List<EnvInfo> loadYQData(){
    Map<String, String> devMap = getDevMap("<div class=\"dtree\"><div class=\"dTreeNode\"><img id=\"id0\" src=\"../envinfo_web/images/img/base.gif\" alt=\"\">废气</div><div id=\"dd0\" class=\"clip\" style=\"display:block;\"><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id1\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd1\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810062&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('1');\">5号高炉炉前除尘燃烧排口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id2\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd2\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810061&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('2');\">450烧结除尘燃烧排口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id3\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd3\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810076&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('3');\">炼钢新区转炉二次除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id4\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd4\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810072&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('4');\">炼铁1,2号高炉炉后除尘排口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id5\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd5\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810071&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('5');\">炼铁3号高炉炉后除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id6\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd6\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810067&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('6');\">265㎡烧结机尾除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id7\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd7\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810066&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('7');\">400㎡烧结机尾除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id8\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd8\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810051&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('8');\">450烧结脱硫出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id9\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd9\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810050&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('9');\">450烧结脱硫入口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id10\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd10\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810047&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('10');\">炼铁厂3号高炉热风炉</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id11\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd11\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810046&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('11');\">炼铁厂4号高炉热风炉</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id12\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd12\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810032&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('12');\">265烧结烟气脱硫出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id13\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd13\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810031&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('13');\">400烧结烟气脱硫入口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id14\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd14\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810030&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('14');\">400烧结烟气脱硫出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id15\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd15\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810011&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('15');\">265烧结烟气脱硫入口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id16\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd16\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810077&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('16');\">炼钢老区转炉二次除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id17\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd17\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810075&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('17');\">炼铁1#高炉炉后除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id18\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd18\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810070&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('18');\">炼铁4号高炉炉后除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id19\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd19\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810069&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('19');\">炼铁2号高炉炉前除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id20\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd20\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810068&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('20');\">炼铁1号高炉炉前除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id21\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd21\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810065&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('21');\">炼铁4号高炉炉前除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/join.gif\" alt=\"\"><img id=\"id22\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd22\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810064&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('22');\">炼铁3号高炉炉前除尘出口</a></div><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/joinbottom.gif\" alt=\"\"><img id=\"id23\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd23\" class=\"node\" href=\"enterprisePgasR.do?op=getDefaultChart&amp;devid=YQ6105810063&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('23');\">5号高炉炉后除尘燃烧排口</a></div></div></div>");
    if ( devMap==null || devMap.size()==0 ){
      LOG.info("废气监控设备列表为空！");
      return Collections.EMPTY_LIST;
    }

    List<EnvInfo> envInfoList = new ArrayList<EnvInfo>();
    String url = "http://113.140.66.227:8111/envinfo_web/enterprisePgasR.do";
    for (String name : devMap.keySet()) {
      Map<String, String> params = Http.queryParamsParser(devMap.get(name));
      try {
        List<EnvInfo> _envInfos = loadData("废气", url, name, params.get("devid"));
        LOG.info("[废气][" + name + "][" + params.get("devid") + "]: " + _envInfos.size());
        if ( _envInfos!=null ){
          envInfoList.addAll(_envInfos);
        }
      } catch (Exception e) {
        LOG.error("[废气][" + name + ", "  + params.get("devid") + "]抓取失败!");
      }
    }
    return envInfoList;
  }

  /**
   * 污水监控
   */
  public static  List<EnvInfo> loadWSData(){
    Map<String, String> devMap = getDevMap("<div class=\"dtree\"><div class=\"dTreeNode\"><img id=\"id0\" src=\"../envinfo_web/images/img/base.gif\" alt=\"\">污水</div><div id=\"dd0\" class=\"clip\" style=\"display:block;\"><div class=\"dTreeNode\"><img src=\"../envinfo_web/images/img/joinbottom.gif\" alt=\"\"><img id=\"id1\" src=\"../envinfo_web/images/img/page.gif\" alt=\"\"><a id=\"sd1\" class=\"node\" href=\"enterprisePwaterR.do?op=getDefaultChart&amp;devid=WS6105810007&amp;flag=1\" target=\"dataframe1\" onclick=\"d.s('1');\">污水处理站总排放口</a></div></div></div>");
    if ( devMap==null || devMap.size()==0 ){
      LOG.info("废气监控设备列表为空！");
      return Collections.EMPTY_LIST;
    }

    List<EnvInfo> envInfoList = new ArrayList<EnvInfo>();
    String url = "http://113.140.66.227:8111/envinfo_web/enterprisePwaterR.do";
    for (String name : devMap.keySet()) {
      Map<String, String> params = Http.queryParamsParser(devMap.get(name));
      try {
        List<EnvInfo> _envInfos = loadData("污水", url, name, params.get("devid"));
        LOG.info("[污水][" + name + "][" + params.get("devid") + "]: " + _envInfos.size());
        if ( _envInfos!=null ){
          envInfoList.addAll(_envInfos);
        }
      } catch (Exception e) {
        LOG.error("[污水][" + name + ", "  + params.get("devid") + "]抓取失败!");
      }
    }
    return envInfoList;
  }

  /**
   * 污水处理厂监控
   */
  public static  List<EnvInfo> loadFCData() {
    Map<String, String> devMap = getDevMap("<div class=\"dtree\"><div class=\"dTreeNode\"><img id=\"id0\" src=\"../envinfo_web/images/img/base.gif\" alt=\"\">污水处理厂</div></div>");
    if (devMap == null || devMap.size() == 0) {
      LOG.info("污水处理厂监控设备列表为空！");
      return Collections.EMPTY_LIST;
    }

    List<EnvInfo> envInfoList = new ArrayList<EnvInfo>();
    String url = "http://113.140.66.227:8111/envinfo_web/enterprisePwaterR.do";
    for (String name : devMap.keySet()) {
      Map<String, String> params = Http.queryParamsParser(devMap.get(name));
      try {
        List<EnvInfo> _envInfos = loadData("污水处理厂", url, name, params.get("devid"));
        LOG.info("[污水处理厂][" + name + "][" + params.get("devid") + "]: " + _envInfos.size());
        if ( _envInfos!=null ){
          envInfoList.addAll(_envInfos);
        }
      } catch (Exception e) {
        LOG.error("[污水处理厂][" + name + ", "  + params.get("devid") + "]抓取失败!");
      }
    }
    return envInfoList;
  }


  /**
   * 省自动监测系统排放口获取
   */
  public static void getCorpDevList() throws Exception {
    String url = "http://113.140.66.227:8111/envinfo_web/corpDev.do?op=getCorpDevList";

    String html = Http.get(url);

    LOG.info(html);

    Document document = Jsoup.parse(html);
    Elements tables = document.select("table.table");
    if ( tables==null || tables.size()!=2 ){
      LOG.info("页面结构不符合预期！");
      return;
    }

    StringBuilder content = new StringBuilder();

    // 页面上第二个table中是关联排口数据
    Element table = tables.get(1);
    Elements trs = table.select("tr");
    Elements header = trs.get(0).select("th");
    for (Element th : header) {
      LOG.info(org.apache.commons.lang3.StringUtils.trimToEmpty(th.text()) + ", ");
      content.append(org.apache.commons.lang3.StringUtils.trimToEmpty(th.text())).append(", ");
    }
    content.append("设备编号\n");

    //relevanceAll('getGjFqDevs&devId=YQ6105810075&cuscCode=91610581661193700G');return false;
    Pattern pattern = Pattern.compile("&devId=([^&']*)");

    for (int i = 1; i < trs.size(); i++) {
      Elements datas = trs.get(i).select("td");
      for (Element td : datas) {
        LOG.info(td.text());
        content.append(org.apache.commons.lang3.StringUtils.trimToEmpty(td.text())).append(", ");
      }

      Element  link = trs.get(i).select("a").first();
      LOG.info(link.attr("onclick"));

      Matcher matcher = pattern.matcher(link.attr("onclick"));
      if ( matcher.find() ){
        LOG.info("Found value: " + matcher.group(1) );
        content.append(matcher.group(1));
      }
      content.append("\n");
      System.out.println("");
    }

    LOG.info("数据： \n" + content);
  }

  private static Map<String, String> getDevMap(String html) {

    Map<String, String> devMap = new HashMap<String, String>();

    Document document = Jsoup.parse(html);

    String name = "";
    Element root = document.select("div.dTreeNode").first();
    if ( root!=null ){
      name = root.text();
    }

    LOG.info(name);
    Elements wsListlinks = document.select("a.node");
    for (Element link : wsListlinks) {
      devMap.put(name + "-" + link.text(), link.attr("href"));
      LOG.info("\t" + name + "-" + link.text() + " ==> " + link.attr("href"));
    }
    return devMap;
  }

  /**
   * 获取数据
   */
  public static List<EnvInfo> loadData(String type, String url, String name, String devid) throws Exception {
    Map<String, String> formData = new HashMap<String, String>();
    formData.put("op", "toList");
    formData.put("devid", devid);
    formData.put("pageResult.pageNo", "1");
    formData.put("pageResult.orderBy", "");
    formData.put("pageResult.sort", "asc");
    formData.put("pageResult.pageSize", "150");

    LOG.info("[" + type + "]Name: " + name + ": Devid: " + devid);

    Http.get(url + "?op=getDefaultChart&devid=" + devid + "&flag=1");
    String html = Http.post(url, formData);

    StringBuilder content = new StringBuilder();
    Document doc = Jsoup.parse(html);
    Elements links = doc.select("table.table tr");
    if ( links==null || links.size()==0 ){
      LOG.info("页面数据异常");
      return Collections.EMPTY_LIST;
    }
    Elements tds = links.get(0).select("td");
    for (Element td : tds) {
      String text = td.text();
      content.append(org.apache.commons.lang3.StringUtils.trimToEmpty(text)).append(", ");
    }
    content.append("\n");

    List<EnvInfo> result = new ArrayList<>();
    for (int i = 1; i < links.size(); i++) {
      Element tr = links.get(i);
      tds = tr.select("td");
      if ( tds.size()==1 ){
        continue;
      }

      StringBuilder line = new StringBuilder();
      for (Element td : tds) {
        line.append(org.apache.commons.lang3.StringUtils.trimToEmpty(td.text())).append(", ");
      }
      content.append(line).append("\n");

      Date tehDate = DateUtils.parseDate(tds.get(0).text(), "yyyy-MM-dd HH:mm:ss.S");

      EnvInfo envInfo = new EnvInfo();
      envInfo.setId(devid + tehDate.getTime());
      envInfo.setName(name);
      envInfo.setCode(devid);
      envInfo.setType(type);
      envInfo.setThedate(tehDate);
      envInfo.setData(line.toString());

      result.add(envInfo);
    }

    //LOG.info("数据: \n" + content.toString());


    String filename = name + "-" + devid + DateFormatUtils.format(new Date(), "yyyyMMdd");

    FileUtils.write(new File(filename), content, "UTF-8", true);

    return result;

  }

  /**
   * 系统登录
   * @throws Exception
   */
  public static boolean login() throws Exception {

    Map<String, String> formData = new HashMap<String, String>();
    formData.put("op", "login");
    formData.put("item.usrName", "");
    formData.put("item.usrPassword", "");

    String responseText = Http.post(CONTEXT_PATH + "/loginAndRegister.do", formData);

    return StringUtils.contains(responseText, "导航菜单");
  }

  public static String MD5(byte[] input) throws Exception {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    byte[] out = md5.digest(input);
    return Base64.getEncoder().encodeToString(out);
  }

}
