package com.mingsoft.bbs.parser.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mingsoft.comment.entity.CommentEntity;
import com.mingsoft.parser.IParser;
import com.mingsoft.parser.impl.general.DateParser;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.RegexUtil;
import com.mingsoft.util.StringUtil;

public class ChildCommnetListParser  extends IParser{

	/**
	 * 列表临时标签，开始标签
	 */
	private final static String TAB_BEGIN_LIST = "{MS:TAB}";

	/**
	 * 列表临时标签，结束标签
	 */
	private final static String TAB_END_LIST = "{/MS:TAB}";
	
	/**
	 *子列表的临时标签，开始标出
	 */
	

	/**
	 * 列表临时标签，内容规则
	 */
	private final static String TAB_BODY = "\\{MS:TAB\\}([\\s\\S]*?)\\{/MS:TAB}";
	


	
	
	
	/**
	 * 子评论的列表标签的开始位置
	 */
	private final static String CHILD_COMLIST_BEGIN = "\\{ms:chidlcomlist.*?\\}";
	
	/**
	 * 子评论的列表标签的结束位置
	 */
	private final static String CHILD_COMLIST_END = "\\{/ms:chidlcomlist\\}";

	/**
	 * 评论列表的属性 类型String 取值范围：son|top son表示下级栏目(默认值) top顶级栏目（非必填） 栏目父标签
	 * {ms:channel type=”son” typeid=””}
	 */
	public static final String COMLIST_TYPE = "type";
	/**
	 * 栏目列表的属性 类型String 取值范围：son|top | top 默认帖子下的评论列表 son 评论内的评论列表 {ms:channel
	 * type=”son”}
	 */
	public static final String COMLIST_TYPE_SON = "son";
	
	
	public static final String COMLIST_TYPE_TOP = "top";

	/**
	 * 列表中的属性，size 类型 int 返回文档列表总数,默认为20条全部返回，也可以配合分页使用 评论列表属性标签 非必填
	 */
	public static final String LIST_SIZE = "size";

	/**
	 * 评论列表中的属性, 默认(desc)升序，asc降序
	 */
	public static final String LIST_ORDER = "order";
	/**
	 * 降序
	 */
	public static final String LIST_ASC = "asc";
	
	
	
	/**
	 * 评论者的用户id标签:[field.people.id/]
	 */
	protected final static String COMLIST_PEOPLE_ID = "\\[field.people.id/\\]";

	// =======================================================================

	/**
	 * 评论回复人名称 [field.people.name/]
	 */
	protected final static String COMLIST_PEOPLENAME = "\\[field.people.name/\\]";

	/**
	 * 评论回复者头像[field.people.icon/]
	 */
	protected final static String COMLIST_PEOPLEICON = "\\[field.people.icon/\\]";

	/**
	 * 评论回复时间[field.date.comment fmt=yyyy-mm-dd hh:mm:ss/]
	 */
	protected final static String COMLIST_TIME_LAST_TIME = "\\[field.date.comment\\s{0,}(fmt=(.*?))?/]";

	/**
	 * 评论内容 length:内容的长度,指定获取文章长度)(非必填) [field.content length=/]
	 */
	protected final static String COMLIST_CONTENT_FIELD = "\\[field.content\\s{0,}(length=(\\d*).{0,})?/]";
	/**
	 * 评论列表子标签:序号,根据显示条数显示的序号1 2 …..10 评论列表子标签 [field.index/]
	 */
	protected final static String COMLIST_INDEX_FIELD = "\\[field.index/\\]";

	/**
	 * 评论列表子标签:评论id[field.id/]
	 */
	protected final static String COMLIST_ID_FIELD = "\\[field.id/\\]";
	
	/**
	 * 评论列表子标签:默认评论类型匿名为1
	 */
	protected final static String COMLIST_DISPLYTYPE = "\\[field.comment.type/\\]";

	/**
	 * 默认评论作者头像
	 */
	protected final static String COMMENT_AUTHOR_ICON = "<!--未找到该标签内容-->";

	/**
	 * 构造标签的属性
	 * 
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 * @return
	 */
	public ChildCommnetListParser(String htmlContent, List<CommentEntity> newContent) {
		// 在HTML模版中标记出要用内容替换的标签
		String htmlCotents = channelPrplace(htmlContent, TAB_BEGIN_LIST, CHILD_COMLIST_BEGIN);
		htmlCotents = channelPrplace(htmlCotents, TAB_END_LIST, CHILD_COMLIST_END);
		// 经过遍历后的数组标签
		super.newCotent = commenList(htmlCotents, newContent);
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
	private String commenList(String htmlCotent, List<CommentEntity> commenList) {
		// 在替换好标签的HTML代码中将用标签替换的那段HTML代码截取出来
		String tabHtml = tabHtml(htmlCotent);
		String htmlList = "";
		// 初始楼层
		if (commenList.size() != 0) {
			for (int i = 0; i < commenList.size(); i++) {
				// 获取绑定的评论实体
				CommentEntity comment = commenList.get(i);
				// 获取绑定的people实体
				if(comment.getCommentPeopleUser()!=null){
					//默认是取用户名
					String peopleNickName = comment.getCommentPeopleUser().getPeopleName();
					// 替换评论内容
					htmlList += tabContent(tabHtml, comment.getCommentContent(), COMLIST_CONTENT_FIELD);
					String peopelUserIcon = String.valueOf(comment.getCommentPeopleUser().getPuIcon());
					// 如果评论人昵称没有则显示评论人帐号
					if (StringUtil.isBlank(peopleNickName)) {
						peopleNickName = comment.getCommentPeopleUser().getPuNickname();
					}
					htmlList = tabContent(htmlList, peopelUserIcon, COMLIST_PEOPLEICON);
					// 如果评论人昵称没有则显示评论人帐号
					if (StringUtil.isBlank(peopleNickName)) {
						peopleNickName = COMMENT_AUTHOR_ICON;
					}
					htmlList = tabContent(htmlList, peopleNickName, COMLIST_PEOPLENAME);

					// 序号,根据显示条数显示的序号1 2 …..10。
					htmlList = tabContent(htmlList, StringUtil.int2String((i + 1)), COMLIST_INDEX_FIELD);
					// 帖子id
					htmlList = tabContent(htmlList, comment.getCommentId() + "", COMLIST_ID_FIELD);
					//帖子显示类型
					htmlList = tabContent(htmlList, comment.getCommentType() + "", COMLIST_DISPLYTYPE);
					htmlList = tabContent(htmlList, comment.getCommentPeopleUser().getPeopleId()+"",COMLIST_PEOPLE_ID);
					// 替换评论时间
					Date lastTime = comment.getCommentTime();
					DateParser dp = new DateParser(htmlList, lastTime, COMLIST_TIME_LAST_TIME);
					
					htmlList = dp.parse();
					
				}
				
			}
		}
		return htmlList;
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
		int channelNumBegin = count(html, CHILD_COMLIST_BEGIN);
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
			try {
				htmlCotents = matcher.replaceAll(escapeExprSpecialWord(newContent));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return htmlCotents;
	}
	
	/** 
	 * 转义正则特殊字符 （$()*+.[]?\^{},|） 
	 * @param keyword 
	 * @return 
	 */  
	public static String escapeExprSpecialWord(String keyword) { 
	    if (!StringUtil.isBlank(keyword)) {  
	        String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };  
	        for (String key : fbsArr) {
	            if (keyword.contains(key)) {  
	                keyword = keyword.replace(key, "\\" + key);  
	            }  
	        }  
	    }
	    return keyword;  
	}  

	
	/**
	 * 替换特殊字符
	 * 
	 * @param data
	 *            需要处理的数据
	 * @return 替换好的数据
	 */
	protected String replaceSpecialChar(String data) {
		String regex = "\\$\\{(.+?)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			//data = data.replaceAll("", "");
		}
		return sb.toString();
	}

	/**
	 * 定位栏目标签中所有的属性
	 */
	private final static String COMLIST_PROPERTY = "\\{ms:comlist(.*)?\\}";

	/**
	 * 取出栏目标签中的属性
	 * 
	 * @param html
	 *            HTML模版
	 * @return 属性集合
	 */
	public static Map<String, String> channelProperty(String html) {
		Map<String, String> listPropertyMap = new HashMap<String, String>();
		String listProperty = parseFirst(html, COMLIST_PROPERTY, 1);

		List<String> listPropertyName = parseAll(listProperty, PRORETY_NAME, 1);
		List<String> listPropertyValue = parseAll(listProperty, PROPERTY_VALUE, 1);
		for (int i = 0; i < listPropertyName.size(); i++) {
			listPropertyMap.put(listPropertyName.get(i).toString(), listPropertyValue.get(i).toString());
		}
		return listPropertyMap;
	}

}
