package com.mingsoft.bbs.parser.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mingsoft.bbs.biz.IForumBiz;
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
 *                    
 */
public class CategoryParser extends IParser {
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
	 * 分类是否显示子分类的属性名
	 * sibling=true：当该分类下没有子分类时显示其同级栏目
	 * sibling=false:当该分类下没有子分类时则不显示
	 */
	public static final String CHANNEL_TYP_SIBLING="sibling";

	/**
	 * 栏目列表的属性 类型String 取值范围：son|top | self son表示下级栏目(默认值) top顶级栏目（非必填） slef
	 * 自身的栏目 {ms:channel type=”son” typeid=””}
	 */
	public static final String CHANNEL_TYPE_SON = "son";
	public static final String CHANNEL_TYPE_TOP = "top";
	public static final String CHANNEL_TYPE_SELF = "self";

	/**
	 * 栏目列表的属性 类型int 默认当前页面的栏目编号（非必填） 栏目父标签 {ms:channel type=”son” typeid=}
	 */
	public static final String CHANNEL_TYPEID = "typeid";

	/**
	 * 查找HTML中栏目列表的正则表达式的开始位置标签 栏目父标签 {ms:bbs.channel type=”sun”}
	 */
	private final static String CHANNEL_BEGIN = "\\{ms:channel.*?\\}";

	/**
	 * 查找HTML中栏目列表的正则表达式的结束位置标签 栏目父标签 {/ms:channel}
	 */
	private final static String CHANNEL_END = "\\{/ms:channel\\}";
	/**
	 * 版块名称[field.typetitle/]
	 */
	private final static String CHANNEL_TITLE = "\\[field.typetitle/\\]";

	/**
	 * 版块连接版块子标签 [field.typelink/]
	 */
	private final static String CHANNEL_LINK = "\\[field.typelink/\\]";

	/*--------------------------------------------------新增标签--------------------------------------------------------*/
	/**
	 * 版主名称[field.moderator/]
	 */
	private final static String CHANNEL_MODERATOR_NAME = "\\[field.moderator/\\]";


	/**
	 * 评论总数量[field.total.comment/]
	 */
	private final static String CHANNEL_COMMENT_NUM = "\\[field.total.comment/\\]";

	/**
	 * 版块ID[field.channelid/]
	 */
	private final static String CHANNEL_ID = "\\[field.channelid/\\]";

	/**
	 * 帖子发帖数量[field.total.subject/]
	 */
	private final static String TOTAL_SUBJECT= "\\[field.total.subject/\\]";
	/**
	 * 帖子今日发帖数量[field.total.today/]
	 */
	private final static String TOTAL_TODAY = "\\[field.total.today/\\]";
	/**
	 * 帖子昨日发帖数量[field.total.yestoday/]
	 */
	private final static String TOTAL_YESTODAY = "\\[field.total.yestoday/\\]";

	/**
	 * 最后发帖时间
	 */
	private final static String CHANNEL_LASTSUBJECT_TIME = "\\[field.date\\s{0,}(fmt=(.*?))?/]";
	
	/**
	 * 版块缩略图[field.litpic/]
	 */
	private final static String CHANNEL_MODERATOR_ICON = "\\[field.litpic/\\]";
	
	/**
	 * 版块描述[field.descrip/]
	 */
	private final static String CHANNEL_MODERATOR_DESCRIP = "\\[field.descrip/\\]";
	
	/**
	 * 当前选中类别样式
	 */
	public static final String CHANNEL_FIELD_CLASS = "\\[field.class/\\]";
	
	/**
	 * 栏目显示序号
	 */
	public static final String CHANNEL_FIELD_INDEX = "\\[field.index/\\]";
	
	/**
	 * 连接地址
	 */
	private String link;
	
	/**
	 * 板块业务层
	 */
	private IForumBiz categoryBiz;
	
	/**
	 * 当前栏目
	 */
	private int curColumnId;

	/**
	 * 选中样式
	 */
	private String className;


	/**
	 * 构造标签的属性
	 * 
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public CategoryParser(String htmlContent, List<ForumEntity> categoryList,String link,IForumBiz categoryBiz) {
		this.categoryBiz = categoryBiz;
		this.link = link;
		// 在HTML模版中标记出要用内容替换的标签
		String htmlCotents = channelPrplace(htmlContent, TAB_BEGIN_LIST, CHANNEL_BEGIN);
		htmlCotents = channelPrplace(htmlCotents, TAB_END_LIST, CHANNEL_END);
		// 经过遍历后的数组标签
		super.newCotent = categoryList(htmlCotents, categoryList);
		super.htmlCotent = htmlCotents;
		
	}
	
	/**
	 * 构造标签的属性
	 * 
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public CategoryParser(String htmlContent, List<ForumEntity> categoryList,String link,IForumBiz categoryBiz,int curForumId,String className) {
		this.categoryBiz = categoryBiz;
		this.link = link;
		this.curColumnId=curForumId;
		this.className=className;
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
				List list = categoryBiz.queryForum(null, category.getCategoryId(), category.getCategoryAppId(), category.getCategoryModelId());
				//解析子分类
				ChildCategoryParser ccp = new ChildCategoryParser(tabHtml,list,this.link);
				// 获取栏目id
				int categoryId = category.getCategoryId(); 
				// 连接地址
				html += tabContent(ccp.parse(), StringUtil.buildPath(this.link,category.getCategoryId(),Const.LIST+DO_SUFFIX+"").substring(1), CHANNEL_LINK);
				// 替换栏目标题标签
				html = tabContent(html, category.getCategoryTitle(), CHANNEL_TITLE);
				
				// 替换版块头像
				html = tabContent(html, category.getCategorySmallImg(), CHANNEL_MODERATOR_ICON);
				
				// 替换版块描述
				html = tabContent(html, category.getCategoryDescription(), CHANNEL_MODERATOR_DESCRIP);
				
				html = tabContent(html, StringUtil.int2String(i+1), CHANNEL_FIELD_INDEX);
				//帖子总数
				html = tabContent(html, category.getForumTotalSubject() + "", TOTAL_SUBJECT);
				html = tabContent(html, category.getForumTotalSubject() + "", TOTAL_TODAY);
				html = tabContent(html, category.getForumTotalSubject() + "", TOTAL_YESTODAY);
				html = tabContent(html, category.getCategoryManagerId() + "", CHANNEL_MODERATOR_NAME);
				
				// 查询该栏目下评论总数
				html = tabContent(html, category + "", CHANNEL_COMMENT_NUM);
				// 最后发评论时间
				html = tabContent(html,DateUtil.pastTime(category.getForumLastCommentTime()),CHANNEL_LASTSUBJECT_TIME);
				
				// 板块id
				html = tabContent(html, String.valueOf(categoryId), CHANNEL_ID);
				//选中的样式
				if (this.curColumnId == category.getCategoryId() && !StringUtil.isBlank(className)) {
					html = tabContent(html, className, CHANNEL_FIELD_CLASS);
				} else {
					html = tabContent(html, "", CHANNEL_FIELD_CLASS);
				}
				
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
