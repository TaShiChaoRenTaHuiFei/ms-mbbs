package com.mingsoft.bbs.parser.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mingsoft.bbs.constant.Const;
import com.mingsoft.bbs.entity.ForumEntity;
import com.mingsoft.parser.IParser;
import com.mingsoft.parser.IParserRegexConstant;
import com.mingsoft.util.DateUtil;
import com.mingsoft.util.RegexUtil;
import com.mingsoft.util.StringUtil;

/**
 * 
 * <p>
 *    <b>铭飞-BBS论坛平台</b>   
 * </p>
 *    
 * <p>
 *    Copyright: Copyright (c) 2014 - 2015   
 * </p>
 *       
 * <p>
 *    Company:景德镇铭飞科技有限公司   
 * </p>
 *       @author guoph      @version 300-001-001               
 * <p>
 *             版权所有 铭飞科技            
 * </p>
 *             @ClassName: CategoryCountParser  
 * 
 * @Description: TODO             
 *                    <p>
 *                                Comments:  继承的类 || 实现的接口            
 *                    </p>
 *                                   
 *                    <p>
 *                                Creatr Date:2015-4-22 下午4:01:51            
 *                    </p>
 *                                   
 *                    <p>
 *                                Modification history:            
 *                    </p>
 *                     
 */
public class ChildCategoryParser extends IParser {
	/**
	 * 列表临时标签，开始标签
	 */
	private final static String TAB_BEGIN_LIST = "{MS:TAB}";

	/**
	 * 列表临时标签，结束标签
	 */
	private final static String TAB_END_LIST = "{/MS:TAB}";

	/**
	 * 列表临时标签，内容规则
	 */
	private final static String TAB_BODY = "\\{MS:TAB\\}([\\s\\S]*?)\\{/MS:TAB}";

	/**
	 * 栏目列表的属性 类型String 取值范围：son|top son表示下级栏目(默认值) top顶级栏目（非必填） 栏目父标签
	 * {ms:channel type=”son” typeid=””}
	 */
	public static final String CHANNEL_TYPE = "type";

	/**
	 * 栏目列表的属性 类型String 取值范围：son|top | self son表示下级栏目(默认值) top顶级栏目（非必填） slef
	 * 自身的栏目 {ms:channel type=”son” typeid=””}
	 */
	public static final String CHANNEL_TYPE_SON = "son";
	public static final String CHANNEL_TYPE_TOP = "top";
	public static final String CHANNEL_TYPE_SELF = "self";

	/**
	 * 栏目列表的属性 类型int 默认当前页面的栏目编号（非必填） 栏目父标签 {ms:childchannel type=”sun” typeid=}
	 */
	public static final String CHANNEL_TYPEID = "typeid";

	/**
	 * 查找HTML中栏目列表的正则表达式的开始位置标签 栏目父标签 {ms:childchannel type=”sun”}
	 */
	private final static String CHANNEL_BEGIN = "\\{ms:childchannel.*?\\}";

	/**
	 * 查找HTML中栏目列表的正则表达式的结束位置标签 栏目父标签 {/ms:childchannel}
	 */
	private final static String CHANNEL_END = "\\{/ms:childchannel\\}";
	/**
	 * 栏目名称
	 */
	private final static String CHANNEL_TITLE = "\\[field.typetitle/\\]";

	/**
	 * 栏目连接栏目子标签 [field.typelink/]
	 */
	private final static String CHANNEL_LINK = "\\[field.typelink/\\]";

	/*--------------------------------------------------新增标签--------------------------------------------------------*/
	/**
	 * 版主名称
	 */
	private final static String CHANNEL_MODERATOR_NAME = "\\[field.moderator/\\]";
	/**
	 * 主题总数量
	 */
	private final static String CHANNEL_SUBJECT_NUM = "\\[field.total/\\]";

	/**
	 * 评论总数量
	 */
	private final static String CHANNEL_COMMENT_NUM = "\\[field.total.comment/\\]";

	/**
	 * 栏目ID
	 */
	private final static String CHANNEL_ID = "\\[field.channelid/\\]";
	
	/**
	 * 板块缩略图
	 */
	private final static String CHANNEL_ICON = "\\[field.channel.icon/\\]";

	/**
	 * 帖子今日发帖数量
	 */
	private final static String CHANNEL_TDAYSUBJECT_NUM = "\\[field.total.today/\\]";
	/**
	 * 帖子昨日发帖数量
	 */
	private final static String CHANNEL_YDAYSUBJECT_NUM = "\\[field.total.yestoday/\\]";

	/**
	 * 最后发帖时间
	 */
	private final static String CHANNEL_LASTSUBJECT_TIME = "\\[field.date.last\\s{0,}(fmt=(.*?))?/]";
	
	/**
	 * 连接地址
	 */
	private String link;

	/**
	 * 构造标签的属性
	 * 
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public ChildCategoryParser(String htmlContent, List<ForumEntity> categoryList,String link) {
		this.link = link;
		// 在HTML模版中标记出要用内容替换的标签
		String htmlCotents = channelPrplace(htmlContent, TAB_BEGIN_LIST, CHANNEL_BEGIN);
		htmlCotents = channelPrplace(htmlCotents, TAB_END_LIST, CHANNEL_END);
		// 经过遍历后的数组标签
		super.newCotent = categoryList(htmlCotents, categoryList);
		super.htmlCotent = htmlCotents;
		
	}

	/**
	 * 遍历栏目数组，将取出的内容替换标签 {ms:bbs.channel.title/\\}
	 * 
	 * @param htmlCotent
	 *            原HTML代码
	 * @param categoryList
	 *            板块数组
	 * @param map
	 *            版主信息
	 * 
	 * @return 用内容替换标签后的HTML代码
	 */
	private String categoryList(String htmlCotent, List<ForumEntity> categoryList) {
		// 在替换好标签的HTML代码中将用标签替换的那段HTML代码截取出来
		String tabHtml = tabHtml(htmlCotent);
		String html = "";
		if (categoryList!=null && categoryList.size() >0) {
			for (int i = 0; i < categoryList.size(); i++) {
				ForumEntity category = categoryList.get(i);
				// 获取栏目id
				int categoryId = category.getCategoryId(); 
				// 连接地址
				html += tabContent(tabHtml, StringUtil.buildPath(this.link,category.getCategoryId(),Const.LIST+DO_SUFFIX+"").substring(1), CHANNEL_LINK);
				// 替换栏目标题标签
				html = tabContent(html, category.getCategoryTitle(), CHANNEL_TITLE);
				// 替换栏目缩略图
				html = tabContent(html, category.getCategorySmallImg(), CHANNEL_ICON);
				//帖子总数
				html = tabContent(html, category.getForumTotalSubject() + "", CHANNEL_SUBJECT_NUM);
//				// 查询该栏目下评论总数
				html = tabContent(html, category.getForumCommentCount() + "", CHANNEL_COMMENT_NUM);
//				// 最后发评论时间
				html = tabContent(html,DateUtil.pastTime(category.getForumLastCommentTime()),CHANNEL_LASTSUBJECT_TIME);
				
				// 板块id
				html = tabContent(html, String.valueOf(categoryId), CHANNEL_ID);
			}
		} else {
			html = IParserRegexConstant.REGEX_CHANNEL_ERRO;
		}
		return html;
	}

	/**
	 * 在替换好标签的HTML代码中将用标签替换的那段HTML代码截取出来
	 * 
	 * @param htmlCotent
	 *            替换好标签后的HTML代码
	 * @return 标签替换的那段HTML代码截取出来
	 */
	private String tabHtml(String htmlCotent) {
		Pattern patternList = Pattern.compile(TAB_BODY);
		Matcher matcherList = patternList.matcher(htmlCotent);
		if (matcherList.find()) {
			htmlCotent = matcherList.group(1);
		}
		return htmlCotent;
	}

	/**
	 * 将剔除标签后的内容输出
	 */
	@Override
	public String parse() {
		// TODO Auto-generated method stub
		String channelHtml = this.replaceFirst(TAB_BODY);
		return channelHtml;
	}

	/**
	 * 获取模版文件中栏目列表的个数
	 * 
	 * @param html
	 *            文件模版
	 * @return 返回该字符串的个数
	 */
	public static int channelNum(String html) {
		int channelNumBegin = count(html, CHANNEL_BEGIN);
		return channelNumBegin;
	}

	/**
	 * 将用需要用内容替换的标签换成标记标签
	 * 
	 * @param htmlCotent
	 *            原HTML文件
	 * @return 替换好标签后的HTNL文件
	 */
	private String channelPrplace(String htmlCotent, String regexTab, String regex) {
		String htmlCotents = "";
		super.htmlCotent = htmlCotent;
		super.newCotent = regexTab;
		htmlCotents = this.replaceFirst(regex);
		if (htmlCotents.equals("")) {
			htmlCotents = "标签格式错误";
		}
		return htmlCotents;
	}

	@Override
	public String replaceFirst(String regex) {
		return RegexUtil.replaceFirst(htmlCotent, regex, newCotent);
	}

	/**
	 * 替换的数组内容
	 * 
	 * @param htmlCotent
	 *            用标记标签替换好的HTML模版代码
	 * @param newContent
	 *            需要插入数组的内容
	 * @return 如果存在该标签返回替换后的标签和内容，如果不存在则返回空
	 */
	private String tabContent(String htmlCotent, String newContent, String regex) {
		if (StringUtil.isBlank(newCotent)) {
			newCotent = regex;
		}
		String htmlCotents = htmlCotent;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(htmlCotent);
		if (matcher.find()) {
			htmlCotents = matcher.replaceAll(newContent.toString().replace("\\", "/"));
		}
		return htmlCotents;
	}

	/**
	 * 定位栏目标签中所有的属性
	 */
	private final static String CHANNEL_PROPERTY = "\\{ms:channel(.*)?\\}";

	/**
	 * 取出栏目标签中的属性
	 * 
	 * @param html
	 *            HTML模版
	 * @return 属性集合
	 */
	public static Map<String, String> channelProperty(String html) {
		Map<String, String> listPropertyMap = new HashMap<String, String>();
		String listProperty = parseFirst(html, CHANNEL_PROPERTY, 1);

		List<String> listPropertyName = parseAll(listProperty, PRORETY_NAME, 1);
		List<String> listPropertyValue = parseAll(listProperty, PROPERTY_VALUE, 1);
		for (int i = 0; i < listPropertyName.size(); i++) {
			listPropertyMap.put(listPropertyName.get(i).toString(), listPropertyValue.get(i).toString());
		}
		return listPropertyMap;
	}
}
