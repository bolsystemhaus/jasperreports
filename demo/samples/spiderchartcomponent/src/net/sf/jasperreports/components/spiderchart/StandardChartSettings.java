/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.components.spiderchart;

import java.awt.Color;

import net.sf.jasperreports.charts.type.EdgeEnum;
import net.sf.jasperreports.engine.JRAnchor;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRHyperlinkParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.base.JRBaseHyperlink;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;
import net.sf.jasperreports.engine.type.HyperlinkTypeEnum;


/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id$
 */
public class StandardChartSettings implements ChartSettings, JRChangeEventsSupport
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	/*
	 * Chart properties
	 */
	
	public static final byte CHART_TYPE_SPIDER = 22;
	
	public static final String PROPERTY_LEGEND_BACKGROUND_COLOR = "legendBackgroundColor";
	
	public static final String PROPERTY_LEGEND_COLOR = "legendColor";
	
	public static final String PROPERTY_LEGEND_POSITION = "legendPosition";
	
	public static final String PROPERTY_SHOW_LEGEND = "showLegend";
	
	public static final String PROPERTY_SUBTITLE_COLOR = "subtitleColor";
	
	public static final String PROPERTY_TITLE_COLOR = "titleColor";
	
	public static final String PROPERTY_TITLE_POSITION = "titlePosition";
	
	public static final String PROPERTY_RENDER_TYPE = "renderType";
	
	public static final String PROPERTY_BOOKMARK_LEVEL = "bookmarkLevel";
	
	public static final String PROPERTY_THEME = "theme";
	
	public static final String PROPERTY_ANCHOR_NAME_EXPRESSION = "anchorNameExpression";
	
	public static final String PROPERTY_EVALUATION_GROUP = "evaluationGroup";
	
	public static final String PROPERTY_EVALUATION_TIME = "evaluationTime";
	
	public static final String PROPERTY_CHART_TYPE = "chartType";
	
	public static final String PROPERTY_CUSTOMIZER_CLASS = "customizerClass";
	
	public static final String PROPERTY_DATASET = "dataset";
	
	public static final String PROPERTY_LEGEND_FONT = "legendFont";
	
	public static final String PROPERTY_SUBTITLE_EXPRESSION = "subtitleExpression";
	
	public static final String PROPERTY_SUBTITLE_FONT = "subtitleFont";
	
	public static final String PROPERTY_TITLE_EXPRESSION = "titleExpression";
	
	public static final String PROPERTY_TITLE_FONT = "titleFont";
	
	public static final String PROPERTY_HYPERLINK_ANCHOR_EXPRESSION = "hyperlinkAnchorExpression";
	
	public static final String PROPERTY_HYPERLINK_PAGE_EXPRESSION = "hyperlinkPageExpression";
	
	public static final String PROPERTY_HYPERLINK_REFERENCE_EXPRESSION = "hyperlinkReferenceExpression";
	
	public static final String PROPERTY_HYPERLINK_TARGET = "hyperlinkTarget";
	
	public static final String PROPERTY_LINK_TARGET = "linkTarget";
	
	public static final String PROPERTY_HYPERLINK_TOOLTIP_EXPRESSION = "hyperlinkTooltipExpression";
	
	public static final String PROPERTY_LINK_TYPE = "linkType";
	
	public static final String PROPERTY_HYPERLINK_PARAMETERS = "hyperlinkParameters";
	
	
	/**
	 *
	 */
	protected Byte chartType = CHART_TYPE_SPIDER;

	/**
	 *
	 */
	protected Boolean showLegend = null;
	protected String linkType;
	protected String linkTarget;
	private JRHyperlinkParameter[] hyperlinkParameters;
	
	protected Color titleColor = null;
	protected Color subtitleColor = null;
	protected Color legendColor = null;
	protected Color legendBackgroundColor = null;
	protected EdgeEnum legendPosition = null;
	protected EdgeEnum titlePosition = null;

	protected String renderType;

	/**
	 *
	 */
	protected JRFont titleFont = null;
	protected JRFont subtitleFont = null;
	protected JRFont legendFont = null;

	/**
	 *
	 */
	protected JRExpression titleExpression = null;
	protected JRExpression subtitleExpression = null;
	protected JRExpression anchorNameExpression = null;
	protected JRExpression hyperlinkReferenceExpression = null;
	protected JRExpression hyperlinkAnchorExpression = null;
	protected JRExpression hyperlinkPageExpression = null;
	private JRExpression hyperlinkTooltipExpression;

	/**
	 * The bookmark level for the anchor associated with this chart.
	 * @see JRAnchor#getBookmarkLevel()
	 */
	protected int bookmarkLevel = JRAnchor.NO_BOOKMARK;

	/**
	 *
	 */
	public StandardChartSettings()
	{

	}
	
	
	public StandardChartSettings(ChartSettings chart, JRBaseObjectFactory factory)
	{
		showLegend = chart.getShowLegend();
		linkType = chart.getLinkType();
		linkTarget = chart.getLinkTarget();
		titlePosition = chart.getTitlePosition();
		titleColor = chart.getTitleColor();
		subtitleColor = chart.getSubtitleColor();
		legendColor = chart.getLegendColor();
		legendBackgroundColor = chart.getLegendBackgroundColor();
		legendPosition = chart.getLegendPosition();
		renderType = chart.getRenderType();
		
		titleFont = chart.getTitleFont();
		subtitleFont = chart.getSubtitleFont();
		legendFont = chart.getLegendFont();
		titleExpression = factory.getExpression(chart.getTitleExpression());

		subtitleExpression = factory.getExpression(chart.getSubtitleExpression());
		anchorNameExpression = factory.getExpression(chart.getAnchorNameExpression());
		hyperlinkReferenceExpression = factory.getExpression(chart.getHyperlinkReferenceExpression());
		hyperlinkAnchorExpression = factory.getExpression(chart.getHyperlinkAnchorExpression());
		hyperlinkPageExpression = factory.getExpression(chart.getHyperlinkPageExpression());
		hyperlinkTooltipExpression = factory.getExpression(chart.getHyperlinkTooltipExpression());
		bookmarkLevel = chart.getBookmarkLevel();
		hyperlinkParameters = JRBaseHyperlink.copyHyperlinkParameters(chart, factory);
	}
	/**
	 * 
	 */
	public Boolean getShowLegend()
	{
		return this.showLegend;
	}

	/**
	 *
	 */
	public void setShowLegend(Boolean isShowLegend)
	{
		Object old = this.showLegend;
		this.showLegend = isShowLegend;
		getEventSupport().firePropertyChange(PROPERTY_SHOW_LEGEND, old, this.showLegend);
	}

	/**
	 *
	 */
	public JRFont getTitleFont()
	{
		return titleFont;
	}

	/**
	 *
	 */
	public EdgeEnum getTitlePosition()
	{
		return titlePosition;
	}

	/**
	 *
	 */
	public void setTitlePosition(EdgeEnum titlePosition)
	{
		Object old = this.titlePosition;
		this.titlePosition = titlePosition;
		getEventSupport().firePropertyChange(PROPERTY_TITLE_POSITION, old, this.titlePosition);
	}

	/**
	 *
	 */
	public Color getTitleColor()
	{
		return titleColor;
	}

	/**
	 *
	 */
	public void setTitleColor(Color titleColor)
	{
		Object old = this.titleColor;
		this.titleColor = titleColor;
		getEventSupport().firePropertyChange(PROPERTY_TITLE_COLOR, old, this.titleColor);
	}

	/**
	 *
	 */
	public JRFont getSubtitleFont()
	{
		return subtitleFont;
	}

	/**
	 *
	 */
	public Color getSubtitleColor()
	{
		return subtitleColor;
	}

	/**
	 *
	 */
	public void setSubtitleColor(Color subtitleColor)
	{
		Object old = this.subtitleColor;
		this.subtitleColor = subtitleColor;
		getEventSupport().firePropertyChange(PROPERTY_SUBTITLE_COLOR, old, this.subtitleColor);
	}

	/**
	 *
	 */
	public Color getLegendBackgroundColor() {
		return legendBackgroundColor;
	}

	/**
	 *
	 */
	public Color getLegendColor() {
		return legendColor;
	}

	/**
	 *
	 */
	public JRFont getLegendFont() {
		return legendFont;
	}

	/**
	 *
	 */
	public void setLegendBackgroundColor(Color legendBackgroundColor) {
		Object old = this.legendBackgroundColor;
		this.legendBackgroundColor = legendBackgroundColor;
		getEventSupport().firePropertyChange(PROPERTY_LEGEND_BACKGROUND_COLOR, old, this.legendBackgroundColor);
	}

	/**
	 *
	 */
	public void setLegendColor(Color legendColor) {
		Object old = this.legendColor;
		this.legendColor = legendColor;
		getEventSupport().firePropertyChange(PROPERTY_LEGEND_COLOR, old, this.legendColor);
	}

	/**
	 *
	 */
	public EdgeEnum getLegendPosition()
	{
		return legendPosition;
	}

	/**
	 *
	 */
	public void setLegendPosition(EdgeEnum legendPosition)
	{
		Object old = this.legendPosition;
		this.legendPosition = legendPosition;
		getEventSupport().firePropertyChange(PROPERTY_LEGEND_POSITION, old, this.legendPosition);
	}

	/**
	 * @deprecated Replaced by {@link #getHyperlinkTypeValue()}.
	 */
	public byte getHyperlinkType()
	{
		return getHyperlinkTypeValue().getValue();
	}
		
	/**
	 *
	 */
	public HyperlinkTypeEnum getHyperlinkTypeValue()
	{
		return JRHyperlinkHelper.getHyperlinkTypeValue(this);
	}
		
	/**
	 *
	 */
	public byte getHyperlinkTarget()
	{
		return JRHyperlinkHelper.getHyperlinkTarget(this);
	}
		
	/**
	 *
	 */
	public JRExpression getTitleExpression()
	{
		return titleExpression;
	}

	/**
	 *
	 */
	public JRExpression getSubtitleExpression()
	{
		return subtitleExpression;
	}

	/**
	 *
	 */
	public JRExpression getAnchorNameExpression()
	{
		return anchorNameExpression;
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkReferenceExpression()
	{
		return hyperlinkReferenceExpression;
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkAnchorExpression()
	{
		return hyperlinkAnchorExpression;
	}

	/**
	 *
	 */
	public JRExpression getHyperlinkPageExpression()
	{
		return hyperlinkPageExpression;
	}
	
	public byte getChartType()
	{
		return chartType;
	}

	/**
	 *
	 */
	public String getRenderType()
	{
		return renderType;
	}

	/**
	 *
	 */
	public void setRenderType(String renderType)
	{
		Object old = this.renderType;
		this.renderType = renderType;
		getEventSupport().firePropertyChange(PROPERTY_RENDER_TYPE, old, this.renderType);
	}

	public int getBookmarkLevel()
	{
		return bookmarkLevel;
	}

	/**
	 *
	 */
	public void setBookmarkLevel(int bookmarkLevel)
	{
		Object old = this.bookmarkLevel;
		this.bookmarkLevel = bookmarkLevel;
		getEventSupport().firePropertyChange(PROPERTY_BOOKMARK_LEVEL, old, this.bookmarkLevel);
	}


	public String getLinkType()
	{
		return linkType;
	}
	
	public String getLinkTarget()
	{
		return linkTarget;
	}


	public JRHyperlinkParameter[] getHyperlinkParameters()
	{
		return hyperlinkParameters;
	}
	
	
	public JRExpression getHyperlinkTooltipExpression()
	{
		return hyperlinkTooltipExpression;
	}


	/**
	 * @param chartType the chartType to set
	 */
	public void setChartType(Byte chartType) {
		Object old = this.chartType;
		this.chartType = chartType;
		getEventSupport().firePropertyChange(PROPERTY_CHART_TYPE, old, this.chartType);
	}
	
	/**
	 * @param linkType the linkType to set
	 */
	public void setLinkType(String linkType) {
		Object old = this.linkType;
		this.linkType = linkType;
		getEventSupport().firePropertyChange(PROPERTY_LINK_TYPE, old, this.linkType);
	}
	/**
	 * @param linkTarget the linkTarget to set
	 */
	public void setLinkTarget(String linkTarget) {
		Object old = this.linkTarget;
		this.linkTarget = linkTarget;
		getEventSupport().firePropertyChange(PROPERTY_LINK_TARGET, old, this.linkTarget);
	}

	/**
	 * @param titleFont the titleFont to set
	 */
	public void setTitleFont(JRFont titleFont) {
		Object old = this.linkTarget;
		this.titleFont = titleFont;
		getEventSupport().firePropertyChange(PROPERTY_TITLE_FONT, old, this.titleFont);
	}
	/**
	 * @param subtitleFont the subtitleFont to set
	 */
	public void setSubtitleFont(JRFont subtitleFont) {
		Object old = this.subtitleFont;
		this.subtitleFont = subtitleFont;
		getEventSupport().firePropertyChange(PROPERTY_SUBTITLE_FONT, old, this.subtitleFont);
	}
	/**
	 * @param legendFont the legendFont to set
	 */
	public void setLegendFont(JRFont legendFont) {
		Object old = this.legendFont;
		this.legendFont = legendFont;
		getEventSupport().firePropertyChange(PROPERTY_LEGEND_FONT, old, this.legendFont);
	}

	/**
	 * @param hyperlinkParameters the hyperlinkParameters to set
	 */
	public void setHyperlinkParameters(JRHyperlinkParameter[] hyperlinkParameters) {
		Object old = this.hyperlinkParameters;
		this.hyperlinkParameters = hyperlinkParameters;
		getEventSupport().firePropertyChange(PROPERTY_HYPERLINK_PARAMETERS, old, this.hyperlinkParameters);
	}

	/**
	 * @param titleExpression the titleExpression to set
	 */
	public void setTitleExpression(JRExpression titleExpression) {
		Object old = this.titleExpression;
		this.titleExpression = titleExpression;
		getEventSupport().firePropertyChange(PROPERTY_TITLE_EXPRESSION, old, this.titleExpression);
	}


	/**
	 * @param subtitleExpression the subtitleExpression to set
	 */
	public void setSubtitleExpression(JRExpression subtitleExpression) {
		Object old = this.subtitleExpression;
		this.subtitleExpression = subtitleExpression;
		getEventSupport().firePropertyChange(PROPERTY_SUBTITLE_EXPRESSION, old, this.subtitleExpression);
	}


	/**
	 * @param anchorNameExpression the anchorNameExpression to set
	 */
	public void setAnchorNameExpression(JRExpression anchorNameExpression) {
		Object old = this.anchorNameExpression;
		this.anchorNameExpression = anchorNameExpression;
		getEventSupport().firePropertyChange(PROPERTY_ANCHOR_NAME_EXPRESSION, old, this.anchorNameExpression);
	}


	/**
	 * @param hyperlinkReferenceExpression the hyperlinkReferenceExpression to set
	 */
	public void setHyperlinkReferenceExpression(JRExpression hyperlinkReferenceExpression) {
		Object old = this.hyperlinkReferenceExpression;
		this.hyperlinkReferenceExpression = hyperlinkReferenceExpression;
		getEventSupport().firePropertyChange(PROPERTY_HYPERLINK_REFERENCE_EXPRESSION, old, this.hyperlinkReferenceExpression);
	}


	/**
	 * @param hyperlinkAnchorExpression the hyperlinkAnchorExpression to set
	 */
	public void setHyperlinkAnchorExpression(JRExpression hyperlinkAnchorExpression) {
		Object old = this.hyperlinkAnchorExpression;
		this.hyperlinkAnchorExpression = hyperlinkAnchorExpression;
		getEventSupport().firePropertyChange(PROPERTY_HYPERLINK_ANCHOR_EXPRESSION, old, this.hyperlinkAnchorExpression);
	}


	/**
	 * @param hyperlinkPageExpression the hyperlinkPageExpression to set
	 */
	public void setHyperlinkPageExpression(JRExpression hyperlinkPageExpression) {
		Object old = this.hyperlinkPageExpression;
		this.hyperlinkPageExpression = hyperlinkPageExpression;
		getEventSupport().firePropertyChange(PROPERTY_HYPERLINK_PAGE_EXPRESSION, old, this.hyperlinkPageExpression);
	}


	/**
	 * @param hyperlinkTooltipExpression the hyperlinkTooltipExpression to set
	 */
	public void setHyperlinkTooltipExpression(JRExpression hyperlinkTooltipExpression) {
		Object old = this.hyperlinkTooltipExpression;
		this.hyperlinkTooltipExpression = hyperlinkTooltipExpression;
		getEventSupport().firePropertyChange(PROPERTY_HYPERLINK_TOOLTIP_EXPRESSION, old, this.hyperlinkTooltipExpression);
	}

	public void collectExpressions(JRExpressionCollector collector)
	{
		SpiderChartCompiler.collectExpressions(this, collector);
	}

	public Object clone() 
	{
		try
		{
			StandardChartSettings clone = (StandardChartSettings)super.clone();
			
			if (hyperlinkParameters != null)
			{
				clone.hyperlinkParameters = new JRHyperlinkParameter[hyperlinkParameters.length];
				for(int i = 0; i < hyperlinkParameters.length; i++)
				{
					clone.hyperlinkParameters[i] = (JRHyperlinkParameter)hyperlinkParameters[i].clone();
				}
			}
	
			if (titleExpression != null)
			{
				clone.titleExpression = (JRExpression)titleExpression.clone();
			}
			if (subtitleExpression != null)
			{
				clone.subtitleExpression = (JRExpression)subtitleExpression.clone();
			}
			if (anchorNameExpression != null)
			{
				clone.anchorNameExpression = (JRExpression)anchorNameExpression.clone();
			}
			if (hyperlinkReferenceExpression != null)
			{
				clone.hyperlinkReferenceExpression = (JRExpression)hyperlinkReferenceExpression.clone();
			}
			if (hyperlinkAnchorExpression != null)
			{
				clone.hyperlinkAnchorExpression = (JRExpression)hyperlinkAnchorExpression.clone();
			}
			if (hyperlinkPageExpression != null)
			{
				clone.hyperlinkPageExpression = (JRExpression)hyperlinkPageExpression.clone();
			}
			if (hyperlinkTooltipExpression != null)
			{
				clone.hyperlinkTooltipExpression = (JRExpression)hyperlinkTooltipExpression.clone();
			}
			
			return clone;
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	private transient JRPropertyChangeSupport eventSupport;
	
	public JRPropertyChangeSupport getEventSupport()
	{
		synchronized (this)
		{
			if (eventSupport == null)
			{
				eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return eventSupport;
	}
	
}
