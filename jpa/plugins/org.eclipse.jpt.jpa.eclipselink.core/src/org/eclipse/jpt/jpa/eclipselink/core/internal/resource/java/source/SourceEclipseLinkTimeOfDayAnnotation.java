/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NumberIntegerExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TimeOfDayAnnotation;

/**
 * org.eclipse.persistence.annotations.TimeOfDay
 */
public final class SourceEclipseLinkTimeOfDayAnnotation
	extends SourceAnnotation
	implements TimeOfDayAnnotation
{
	private final DeclarationAnnotationElementAdapter<Integer> hourDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> hourAdapter;
	private Integer hour;
	private TextRange hourTextRange;

	private final DeclarationAnnotationElementAdapter<Integer> minuteDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> minuteAdapter;
	private Integer minute;
	private TextRange minuteTextRange;

	private final DeclarationAnnotationElementAdapter<Integer> secondDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> secondAdapter;
	private Integer second;
	private TextRange secondTextRange;

	private final DeclarationAnnotationElementAdapter<Integer> millisecondDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> millisecondAdapter;
	private Integer millisecond;
	private TextRange millisecondTextRange;


	public SourceEclipseLinkTimeOfDayAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
		this.hourDeclarationAdapter = buildHourAdapter(daa);
		this.hourAdapter = new AnnotatedElementAnnotationElementAdapter<Integer>(element, this.hourDeclarationAdapter);
		this.minuteDeclarationAdapter = buildMinuteAdapter(daa);
		this.minuteAdapter = new AnnotatedElementAnnotationElementAdapter<Integer>(element, this.minuteDeclarationAdapter);
		this.secondDeclarationAdapter = buildSecondAdapter(daa);
		this.secondAdapter = new AnnotatedElementAnnotationElementAdapter<Integer>(element, this.secondDeclarationAdapter);
		this.millisecondDeclarationAdapter = buildMillisecondAdapter(daa);
		this.millisecondAdapter = new AnnotatedElementAnnotationElementAdapter<Integer>(element, this.millisecondDeclarationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.hour = this.buildHour(astAnnotation);
		this.hourTextRange = this.buildHourTextRange(astAnnotation);

		this.minute = this.buildMinute(astAnnotation);
		this.minuteTextRange = this.buildMinuteTextRange(astAnnotation);

		this.second = this.buildSecond(astAnnotation);
		this.secondTextRange = this.buildSecondTextRange(astAnnotation);

		this.millisecond = this.buildMillisecond(astAnnotation);
		this.millisecondTextRange = this.buildMillisecondTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncHour(this.buildHour(astAnnotation));
		this.hourTextRange = this.buildHourTextRange(astAnnotation);

		this.syncMinute(this.buildMinute(astAnnotation));
		this.minuteTextRange = this.buildMinuteTextRange(astAnnotation);

		this.syncSecond(this.buildSecond(astAnnotation));
		this.secondTextRange = this.buildSecondTextRange(astAnnotation);

		this.syncMillisecond(this.buildMillisecond(astAnnotation));
		this.millisecondTextRange = this.buildMillisecondTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.hour == null) &&
				(this.minute == null) &&
				(this.second == null) &&
				(this.millisecond == null);
	}


	// ********** TimeOfDayAnnotation implementation **********

	// ***** hour
	public Integer getHour() {
		return this.hour;
	}

	public void setHour(Integer hour) {
		if (this.attributeValueHasChanged(this.hour, hour)) {
			this.hour = hour;
			this.hourAdapter.setValue(hour);
		}
	}

	private void syncHour(Integer astHour) {
		Integer old = this.hour;
		this.hour = astHour;
		this.firePropertyChanged(HOUR_PROPERTY, old, astHour);
	}

	private Integer buildHour(Annotation astAnnotation) {
		return this.hourAdapter.getValue(astAnnotation);
	}

	public TextRange getHourTextRange() {
		return this.hourTextRange;
	}

	private TextRange buildHourTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.hourDeclarationAdapter, astAnnotation);
	}

	// ***** minute
	public Integer getMinute() {
		return this.minute;
	}

	public void setMinute(Integer minute) {
		if (this.attributeValueHasChanged(this.minute, minute)) {
			this.minute = minute;
			this.minuteAdapter.setValue(minute);
		}
	}

	private void syncMinute(Integer astMinute) {
		Integer old = this.minute;
		this.minute = astMinute;
		this.firePropertyChanged(MINUTE_PROPERTY, old, astMinute);
	}

	private Integer buildMinute(Annotation astAnnotation) {
		return this.minuteAdapter.getValue(astAnnotation);
	}

	public TextRange getMinuteTextRange() {
		return this.minuteTextRange;
	}

	private TextRange buildMinuteTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.minuteDeclarationAdapter, astAnnotation);
	}

	// ***** second
	public Integer getSecond() {
		return this.second;
	}

	public void setSecond(Integer newSecond) {
		if (this.attributeValueHasChanged(this.second, newSecond)) {
			this.second = newSecond;
			this.secondAdapter.setValue(newSecond);
		}
	}

	private void syncSecond(Integer astSecond) {
		Integer old = this.second;
		this.second = astSecond;
		this.firePropertyChanged(SECOND_PROPERTY, old, astSecond);
	}

	private Integer buildSecond(Annotation astAnnotation) {
		return this.secondAdapter.getValue(astAnnotation);
	}

	public TextRange getSecondTextRange() {
		return this.secondTextRange;
	}

	private TextRange buildSecondTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.secondDeclarationAdapter, astAnnotation);
	}

	// ***** millisecond
	public Integer getMillisecond() {
		return this.millisecond;
	}

	public void setMillisecond(Integer millisecond) {
		if (this.attributeValueHasChanged(this.millisecond, millisecond)) {
			this.millisecond = millisecond;
			this.millisecondAdapter.setValue(millisecond);
		}
	}

	private void syncMillisecond(Integer astMillisecond) {
		Integer oldMillisecond = this.millisecond;
		this.millisecond = astMillisecond;
		this.firePropertyChanged(MILLISECOND_PROPERTY, oldMillisecond, astMillisecond);
	}

	private Integer buildMillisecond(Annotation astAnnotation) {
		return this.millisecondAdapter.getValue(astAnnotation);
	}

	public TextRange getMillisecondTextRange() {
		return this.millisecondTextRange;
	}

	private TextRange buildMillisecondTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.millisecondDeclarationAdapter, astAnnotation);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<Integer> buildHourAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(daa, EclipseLink.TIME_OF_DAY__HOUR, NumberIntegerExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildMinuteAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(daa, EclipseLink.TIME_OF_DAY__MINUTE, NumberIntegerExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildSecondAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(daa, EclipseLink.TIME_OF_DAY__SECOND, NumberIntegerExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildMillisecondAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(daa, EclipseLink.TIME_OF_DAY__MILLISECOND, NumberIntegerExpressionConverter.instance());
	}

}
