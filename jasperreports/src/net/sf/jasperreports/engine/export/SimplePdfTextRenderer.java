/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2023 Cloud Software Group, Inc. All rights reserved.
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
package net.sf.jasperreports.engine.export;

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator.Attribute;

import java.util.Locale;
import java.util.Map;

import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.type.HyperlinkTypeEnum;
import net.sf.jasperreports.engine.type.RunDirectionEnum;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.jasperreports.engine.util.JRTextAttribute;
import net.sf.jasperreports.export.pdf.PdfPhrase;
import net.sf.jasperreports.export.pdf.PdfProducer;
import net.sf.jasperreports.export.pdf.PdfTextAlignment;
import net.sf.jasperreports.export.pdf.PdfTextChunk;
import net.sf.jasperreports.export.pdf.PdfTextRendererContext;
import net.sf.jasperreports.export.pdf.TextDirection;
import net.sf.jasperreports.export.pdf.classic.ClassicChunk;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class SimplePdfTextRenderer extends AbstractPdfTextRenderer
{
	private float yLine = 0;
	/**
	 * @deprecated To be removed.
	 */
	protected boolean legacyTextMeasuringFix;
	
	/**
	 * 
	 */
	public SimplePdfTextRenderer(
		JasperReportsContext jasperReportsContext, 
		PdfTextRendererContext context
		)
	{
		super(
			jasperReportsContext, 
			context.getAwtIgnoreMissingFont(),
			context.getIndentFirstLine(),
			context.getJustifyLastLine()
			);
		
		this.legacyTextMeasuringFix = context.getLegacyTextMeasuringFix();
	}

	
	/**
	 * @deprecated Replaced by {@link #SimplePdfTextRenderer(JasperReportsContext, PdfTextRendererContext)}.
	 */
	public SimplePdfTextRenderer(
		JasperReportsContext jasperReportsContext, 
		boolean ignoreMissingFont,
		boolean defaultIndentFirstLine,
		boolean defaultJustifyLastLine
		)
	{
		super(
			jasperReportsContext, 
			ignoreMissingFont,
			defaultIndentFirstLine,
			defaultJustifyLastLine
			);
	}

	
	@Override
	public void initialize(
		JRPdfExporter pdfExporter, 
		PdfProducer pdfProducer,
		JRPdfExporterTagHelper tagHelper,
		JRPrintText text, 
		JRStyledText styledText, 
		int offsetX,
		int offsetY
		)
	{
		super.initialize(
			pdfExporter, 
			pdfProducer,
			tagHelper,
			text, 
			styledText, 
			offsetX,
			offsetY
			);
		
		yLine = 
			pdfExporter.getCurrentPageFormat().getPageHeight()
				- y
				- topPadding
				- verticalAlignOffset
				- text.getLeadingOffset();
	}

	
	@Override
	public void render()
	{
		super.render();
	}

	
	@Override
	protected void renderParagraph(
		AttributedCharacterIterator allParagraphs,
		int paragraphStart,
		String paragraphText
		)
	{
//		tagHelper.startText(text.getLinkType() != null);
		boolean isArtifact = 
				(text.getPropertiesMap().containsProperty("net.sf.jasperreports.export.pdf.tag.artifact") &&
				!"none".equals(text.getPropertiesMap().getProperty("net.sf.jasperreports.export.pdf.tag.artifact")) ? true : false);	

        final JRPropertiesMap jrPropertiesMap = text.getPropertiesMap();
        final String partValue = jrPropertiesMap.getProperty("net.sf.jasperreports.export.pdf.tag.part");

        final boolean part;
        if ("true".equals(partValue)) {
            part = true;
        } else {
            part = false;
        }

        if (part) {
    		if (bulletChunk != null)
    		{
				tagHelper.startText(text.getLinkType() != null, isArtifact);

    			PdfPhrase phrase = pdfProducer.createPhrase();
    			pdfExporter.getPhrase(bulletChunk, bulletText, text, phrase);

    			phrase.go(
    				x + leftPadding,
    				yLine,
    				htmlListIndent + x + leftPadding - 10,
    				pdfExporter.getCurrentPageFormat().getPageHeight()
    					- y
    					- height
    					+ bottomPadding,
    				0,//text.getLineSpacingFactor(),// * text.getFont().getSize(),
    				text.getLineSpacingFactor(),
    				PdfTextAlignment.RIGHT,
    				TextDirection.LTR
    				);

				tagHelper.endText();
    		}

    		bulletText = null;
    		bulletChunk = null;

    		AttributedString paragraph = null;
		
    		if (paragraphText == null)
    		{
    			paragraphText = " ";
    			paragraph = 
    				new AttributedString(
    					paragraphText,
    					new AttributedString(
    						allParagraphs, 
    						paragraphStart, 
    						paragraphStart + paragraphText.length()
    						).getIterator().getAttributes()
    					);
    		}
    		else
    		{
    			paragraph = 
    				new AttributedString(
    					allParagraphs, 
    					paragraphStart, 
    					paragraphStart + paragraphText.length()
    					);
    		}

            final float llx = htmlListIndent + x + leftPadding;
            final float urx = x + width - rightPadding;
            final float ury = pdfExporter.getCurrentPageFormat().getPageHeight()
                    - y
                    - height
                    + bottomPadding
                    - (legacyTextMeasuringFix ? 1 : 0);
            final float fixedLeading = 0; // text.getLineSpacingFactor(),// * text.getFont().getSize(),
            final float multipliedLeading = text.getLineSpacingFactor();
            final TextDirection runDirection = text.getRunDirectionValue() == RunDirectionEnum.LTR
                    ? TextDirection.LTR : TextDirection.RTL;

            final float rectangularWidth = urx - llx;

            AttributedCharacterIterator iterator = paragraph.getIterator();
            Locale locale = pdfExporter.getTextLocale(text);

            int runLimit = 0;
            float minNextLine = yLine;
            float activeLine = yLine;
            float leftWidth = rectangularWidth;
            boolean firstChunk = true;
            while(runLimit < paragraphText.length() && (runLimit = iterator.getRunLimit()) <= paragraphText.length())
            {
                Map<Attribute,Object> attributes = iterator.getAttributes();
                PdfTextChunk chunk = pdfExporter.getChunk(attributes, paragraphText.substring(iterator.getIndex(), runLimit), locale);
			
                JRPrintHyperlink hyperlink = text;
                if (hyperlink.getHyperlinkTypeValue() == HyperlinkTypeEnum.NONE)
                {
                    hyperlink = (JRPrintHyperlink)attributes.get(JRTextAttribute.HYPERLINK);
                }
			
                pdfExporter.setHyperlinkInfo(chunk, hyperlink);

                if (chunk instanceof ClassicChunk) {
                    final ClassicChunk classicChunk = (ClassicChunk) chunk;

                    final Chunk innerChunk = classicChunk.getChunk();

                    final String content = innerChunk.getContent();

                    final int contentLength = content.length();

                    final Font font = innerChunk.getFont();

                    final float size = font.getSize();

                    final BaseFont calculatedBaseFont = font.getCalculatedBaseFont(true);

                    final Map<String, Object> chunkAttributeMap = innerChunk.getChunkAttributes();

                    final boolean link;
                    if (null != chunkAttributeMap) {
                        final Object actionObject = chunkAttributeMap.get(Chunk.ACTION);

                        if (null != actionObject) {
                            link = true;
                        } else {
                            link = false;
                        }
                    } else {
                        link = false;
                    }

                    int previousPartIndex = 0;
                    while (true) {
                        if (contentLength < previousPartIndex + 1) {
                            break;
                        }

                        boolean full = false;
                        boolean complete = false;
                        int lastSeparatorPartIndex = -1;
                        int partIndex = previousPartIndex;
                        float lastSeparatorPartWidth = 0F;
                        float partWidth = 0F;
                        while (true) {
                            if (contentLength < partIndex + 1) {
                                complete = true;

                                break;
                            }

                            final int codePoint = content.codePointAt(partIndex);

                            final int charCount = Character.charCount(codePoint);

                            final int endPartIndex = partIndex + charCount;

                            final String contentSubstring = content.substring(partIndex, endPartIndex);

                            final float charWidth = calculatedBaseFont.getWidthPoint(contentSubstring, size);

                            final float endPartWidth = partWidth + charWidth;

                            if (rectangularWidth < endPartWidth) {
                                break;
                            }

                            if (!full && leftWidth < endPartWidth) {
                                if (lastSeparatorPartIndex > -1) {
                                    break;
                                }

                                full = true;
                            }

                            final boolean whitespace = Character.isWhitespace(codePoint);

                            if (whitespace) {
                                lastSeparatorPartIndex = endPartIndex;
                                lastSeparatorPartWidth = endPartWidth;
                            }

                            partIndex = endPartIndex;
                            partWidth = endPartWidth;
                        }

                        final String partContent;
                        final float resultWidth;
                        if (complete) {
                            partContent = content.substring(previousPartIndex, contentLength);

                            resultWidth = partWidth;

                            previousPartIndex = contentLength;
                        } else if (lastSeparatorPartIndex > -1) {
                            partContent = content.substring(previousPartIndex, lastSeparatorPartIndex);

                            resultWidth = lastSeparatorPartWidth;

                            previousPartIndex = lastSeparatorPartIndex;
                        } else {
                            partContent = content.substring(previousPartIndex, partIndex);

                            resultWidth = partWidth;

                            previousPartIndex = partIndex;
                        }

                        final PdfTextChunk partChunk = pdfExporter.getChunk(attributes, partContent, locale);

                        if (firstChunk)
                        {
                            // only set anchor + bookmark for the first chunk in the text
                            pdfExporter.setAnchor(partChunk, text, text);
                        }

                        pdfExporter.setHyperlinkInfo(partChunk, hyperlink);

                        tagHelper.startText(link, isArtifact);

                        final PdfPhrase partPdfPhrase = pdfProducer.createPhrase();

                        partPdfPhrase.add(partChunk);

                        final float partLlx;
                        final float partLly;
                        if (resultWidth < leftWidth) {
                            partLlx = llx + rectangularWidth - leftWidth;

                            partLly = activeLine;

                            leftWidth = leftWidth - resultWidth;
                        } else {
                            partLlx = llx;

                            partLly = minNextLine;

                            activeLine = minNextLine;

                            minNextLine = yLine;

                            leftWidth = rectangularWidth - resultWidth;
                        }

                        final PdfTextAlignment partAlignment = PdfTextAlignment.LEFT;

                        yLine = partPdfPhrase.go(
                                partLlx,
                                partLly,
                                urx,
                                ury,
                                fixedLeading,
                                multipliedLeading,
                                partAlignment,
                                runDirection);

                        tagHelper.endText();

                        if (yLine < minNextLine) {
                            minNextLine = yLine;
                        } else if (yLine > minNextLine) {
                            yLine = minNextLine;
                        }

                        firstChunk = false;
                    }
                }

                iterator.setIndex(runLimit);
            }
        } else {
    		tagHelper.startText(text.getLinkType() != null, isArtifact);

    		if (bulletChunk != null)
    		{
    			PdfPhrase phrase = pdfProducer.createPhrase();
    			pdfExporter.getPhrase(bulletChunk, bulletText, text, phrase);

    			phrase.go(
    				x + leftPadding,
    				yLine,
    				htmlListIndent + x + leftPadding - 10,
    				pdfExporter.getCurrentPageFormat().getPageHeight()
    					- y
    					- height
    					+ bottomPadding,
    				0,//text.getLineSpacingFactor(),// * text.getFont().getSize(),
    				text.getLineSpacingFactor(),
    				PdfTextAlignment.RIGHT,
    				TextDirection.LTR
    				);
    		}

    		bulletText = null;
    		bulletChunk = null;

    		AttributedString paragraph = null;
		
    		if (paragraphText == null)
    		{
    			paragraphText = " ";
    			paragraph = 
    				new AttributedString(
    					paragraphText,
    					new AttributedString(
    						allParagraphs, 
    						paragraphStart, 
    						paragraphStart + paragraphText.length()
    						).getIterator().getAttributes()
    					);
    		}
    		else
    		{
    			paragraph = 
    				new AttributedString(
    					allParagraphs, 
    					paragraphStart, 
    					paragraphStart + paragraphText.length()
    					);
    		}
		
    		PdfPhrase phrase = pdfProducer.createPhrase();
    		pdfExporter.getPhrase(paragraph, paragraphText, text, phrase);
    		yLine = phrase.go(
    			htmlListIndent + x + leftPadding,
    			yLine,
    			x + width - rightPadding,
    			pdfExporter.getCurrentPageFormat().getPageHeight()
    				- y
    				- height
    				+ bottomPadding
    				- (legacyTextMeasuringFix ? 1 : 0),
    			0,//text.getLineSpacingFactor(),// * text.getFont().getSize(),
    			text.getLineSpacingFactor(),
    			horizontalAlignment == PdfTextAlignment.JUSTIFIED && (isLastParagraph && justifyLastLine) 
    				? PdfTextAlignment.JUSTIFIED_ALL : horizontalAlignment,
    			text.getRunDirectionValue() == RunDirectionEnum.LTR
    				? TextDirection.LTR : TextDirection.RTL
    			);
		
    		tagHelper.endText();
        }
	}


	@Override
	protected AttributedString getAttributedString()
	{
		return styledText.getAttributedString();
	}


	@Override
	public void draw()
	{
		//nothing to do
	}


	@Override
	public boolean addActualText()
	{
		return false;
	}
}
