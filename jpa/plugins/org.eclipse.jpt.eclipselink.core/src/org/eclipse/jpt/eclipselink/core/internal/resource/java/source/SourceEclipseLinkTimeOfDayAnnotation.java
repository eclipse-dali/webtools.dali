/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NumberIntegerExpressionConverter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTimeOfDayAnnotation;

/**
 * org.eclipse.persistence.annotations.TimeOfDay
 */
public final class SourceEclipseLinkTimeOfDayAnnotation
	extends SourceAnnotation<Type>
	implements EclipseLinkTimeOfDayAnnotation
{
	private final DeclarationAnnotationElementAdapter<Integer> hourDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> hourAdapter;
	private Integer hour;

	private final DeclarationAnnotationElementAdapter<Integer> minuteDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> minuteAdapter;
	private Integer minute;

	private final DeclarationAnnotationElementAdapter<Integer> secondDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> secondAdapter;
	private Integer second;

	private final DeclarationAnnotationElementAdapter<Integer> millisecondDeclarationAdapter;
	private final AnnotationElementAdapter<Integer> millisecondAdapter;
	private Integer millisecond;


	public SourceEclipseLinkTimeOfDayAnnotation(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter daa) {
		super(parent, type, daa);
		this.hourDeclarationAdapter = buildHourAdapter(daa);
		this.hourAdapter = new AnnotatedElementAnnotationElementAdapter<Integer>(type, this.hourDeclarationAdapter);
		this.minuteDeclarationAdapter = buildMinuteAdapter(daa);
		this.minuteAdapter = new AnnotatedElementAnnotationElementAdapter<Integer>(type, this.minuteDeclarationAdapter);
		this.secondDeclarationAdapter = buildSecondAdapter(daa);
		this.secondAdapter = new AnnotatedElementAnnotationElementAdapter<Integer>(type, this.secondDeclarationAdapter);
		this.millisecondDeclarationAdapter = buildMillisecondAdapter(daa);
		this.millisecondAdapter = new AnnotatedElementAnnotationElementAdapter<Integer>(type, this.millisecondDeclarationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.hour = this.buildHour(astRoot);
		this.minute = this.buildMinute(astRoot);
		this.second = this.buildSecond(astRoot);
		this.millisecond = this.buildMillisecond(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncHour(this.buildHour(astRoot));
		this.syncMinute(this.buildMinute(astRoot));
		this.syncSecond(this.buildSecond(astRoot));
		this.syncMillisecond(this.buildMillisecond(astRoot));
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

	private Integer buildHour(CompilationUnit astRoot) {
		return this.hourAdapter.getValue(astRoot);
	}

	public TextRange getHourTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.hourDeclarationAdapter, astRoot);
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

	private Integer buildMinute(CompilationUnit astRoot) {
		return this.minuteAdapter.getValue(astRoot);
	}

	public TextRange getMinuteTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.minuteDeclarationAdapter, astRoot);
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

	private Integer buildSecond(CompilationUnit astRoot) {
		return this.secondAdapter.getValue(astRoot);
	}

	public TextRange getSecondTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.secondDeclarationAdapter, astRoot);
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

	private Integer buildMillisecond(CompilationUnit astRoot) {
		return this.millisecondAdapter.getValue(astRoot);
	}

	public TextRange getMillisecondTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.millisecondDeclarationAdapter, astRoot);
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
