package com.mingsoft.bbs.parser.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.bbs.constant.Const;
import com.mingsoft.parser.IParser;
import com.mingsoft.parser.IParserRegexConstant;
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
 *                                Comments:  当前位置解析         
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
public class PostionParser extends IParser {
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

	
	public final static String POSTION_BEGIN = "\\{ms:postion.*?\\}";

	/**
	 * 查找HTML中栏目列表的正则表达式的结束位置标签 栏目父标签 {/ms:channel}
	 */
	private final static String POSTION_END = "\\{/ms:postion\\}";
	/**
	 * 栏目名称
	 */
	private final static String TITLE = "\\[field.typetitle/\\]";

	/**
	 * 栏目连接栏目子标签 [field.typelink/]
	 */
	private final static String LINK = "\\[field.typelink/\\]";

	/*--------------------------------------------------新增标签--------------------------------------------------------*/

	
	/**
	 * 连接地址
	 */
	private String link;
	

/**
 * 构造
 * @param htmlContent html 模板内容
 * @param postion 栏目列表
 * @param link 连接
 */
	public PostionParser(String htmlContent, List<com.mingsoft.basic.entity.CategoryEntity> postion,String link) {
		this.link = link;
		// 在HTML模版中标记出要用内容替换的标签
		String htmlCotents = tapParser(htmlContent, TAB_BEGIN_LIST, POSTION_BEGIN);
		htmlCotents = tapParser(htmlCotents, TAB_END_LIST, POSTION_END);
		// 经过遍历后的数组标签
		super.newCotent = list(htmlCotents, postion);
		super.htmlCotent = htmlCotents;
		
	}

	/**
	 * 处理列表
	 * @param htmlCotent 临时模板内容
	 * @param categoryList 栏目列表
	 * @return 解析后的内容
	 */
	private String list(String htmlCotent, List<CategoryEntity> categoryList) {
		
		// 在替换好标签的HTML代码中将用标签替换的那段HTML代码截取出来
		String tabHtml = tabHtml(htmlCotent);
		String html = "";
		if (categoryList!=null && categoryList.size() >0) {
			for (int i = 0; i < categoryList.size(); i++) {
				CategoryEntity category = categoryList.get(i);
				// 连接地址
				html += tabContent(tabHtml, StringUtil.buildPath(this.link,category.getCategoryId(),Const.LIST+DO_SUFFIX+"").substring(1), LINK);
				// 替换栏目标题标签
				html = tabContent(html, category.getCategoryTitle(), TITLE);
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
	 * 将用需要用内容替换的标签换成标记标签
	 * 
	 * @param htmlCotent
	 *            原HTML文件
	 * @return 替换好标签后的HTNL文件
	 */
	private String tapParser(String htmlCotent, String regexTab, String regex) {
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

}
