/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2013 Jaspersoft Corporation. All rights reserved.
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
package net.sf.jasperreports.export;

import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.export.annotations.ExporterParameter;
import net.sf.jasperreports.export.annotations.ExporterProperty;


/**
 * Interface containing settings used by the pure text exporter.
 *
 * @see JRTextExporter
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id$
 */
public interface TextExporterConfiguration extends ExporterConfiguration
{
	/**
	 * Property whose value is used as default state of the {@link #getCharWidth()} export configuration setting.
	 * <p/>
	 * This property is not set by default.
	 * 
	 * @see JRPropertiesUtil
	 */
	public static final String PROPERTY_CHARACTER_WIDTH = JRPropertiesUtil.PROPERTY_PREFIX + "export.text.character.width";

	/**
	 * Property whose value is used as default state of the {@link #getCharHeight()} export configuration setting.
	 * <p/>
	 * This property is not set by default.
	 * 
	 * @see JRPropertiesUtil
	 */
	public static final String PROPERTY_CHARACTER_HEIGHT = JRPropertiesUtil.PROPERTY_PREFIX + "export.text.character.height";

	/**
	 * Property whose value is used as default state of the {@link #getPageWidthInChars()} export configuration setting.
	 * <p/>
	 * This property is not set by default.
	 * 
	 * @see JRPropertiesUtil
	 */
	public static final String PROPERTY_PAGE_WIDTH = JRPropertiesUtil.PROPERTY_PREFIX + "export.text.page.width";

	/**
	 * Property whose value is used as default state of the {@link #getPageHeightInChars()} export configuration setting.
	 * <p/>
	 * This property is not set by default.
	 * 
	 * @see JRPropertiesUtil
	 */
	public static final String PROPERTY_PAGE_HEIGHT = JRPropertiesUtil.PROPERTY_PREFIX + "export.text.page.height";

	/**
	 * Returns a float value representing the pixel/character horizontal ratio.
	 * @see #PROPERTY_CHARACTER_WIDTH
	 */
	@SuppressWarnings("deprecation")
	@ExporterParameter(
		type=net.sf.jasperreports.engine.export.JRTextExporterParameter.class, 
		name="CHARACTER_WIDTH"
		)
	@ExporterProperty(
		value=PROPERTY_CHARACTER_WIDTH, 
		intDefault=0
		)
	public Float getCharWidth();

	/**
	 * Returns a float value representing the pixel/character vertical ratio.
	 * @see #PROPERTY_CHARACTER_HEIGHT
	 */
	@SuppressWarnings("deprecation")
	@ExporterParameter(
		type=net.sf.jasperreports.engine.export.JRTextExporterParameter.class, 
		name="CHARACTER_HEIGHT"
		)
	@ExporterProperty(
		value=PROPERTY_CHARACTER_HEIGHT, 
		intDefault=0
		)
	public Float getCharHeight();

	/**
	 * Returns an integer representing the page width in characters.
	 * @see #PROPERTY_PAGE_WIDTH
	 */
	@SuppressWarnings("deprecation")
	@ExporterParameter(
		type=net.sf.jasperreports.engine.export.JRTextExporterParameter.class, 
		name="PAGE_WIDTH"
		)
	@ExporterProperty(
		value=PROPERTY_PAGE_WIDTH, 
		intDefault=0
		)
	public Integer getPageWidthInChars();

	/**
	 * Returns an integer representing the page height in characters.
	 * @see #PROPERTY_PAGE_HEIGHT
	 */
	@SuppressWarnings("deprecation")
	@ExporterParameter(
		type=net.sf.jasperreports.engine.export.JRTextExporterParameter.class, 
		name="PAGE_HEIGHT"
		)
	@ExporterProperty(
		value=PROPERTY_PAGE_HEIGHT, 
		intDefault=0
		)
	public Integer getPageHeightInChars();
	
	/**
	 * Returns a string representing text that will be inserted between pages of the generated report. By default, JasperReports
	 * separates pages by two empty lines, but this behavior can be overridden by this parameter.
	 */
	@SuppressWarnings("deprecation")
	@ExporterParameter(
		type=net.sf.jasperreports.engine.export.JRTextExporterParameter.class, 
		name="BETWEEN_PAGES_TEXT"
		)
	public String getBetweenPagesText();
	
	/**
	 * Returns a string representing the separator between two lines of text. This parameter is useful since line separators can
	 * vary from one operating system to another. The default value is the system "line.separator" property.
	 */
	@SuppressWarnings("deprecation")
	@ExporterParameter(
		type=net.sf.jasperreports.engine.export.JRTextExporterParameter.class, 
		name="LINE_SEPARATOR"
		)
	public String getLineSeparator();
}
