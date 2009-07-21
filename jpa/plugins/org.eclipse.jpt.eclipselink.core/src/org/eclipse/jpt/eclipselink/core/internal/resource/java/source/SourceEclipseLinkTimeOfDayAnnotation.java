/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NumberIntegerExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
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
		this.hourAdapter = new ShortCircuitAnnotationElementAdapter<Integer>(type, this.hourDeclarationAdapter);
		this.minuteDeclarationAdapter = buildMinuteAdapter(daa);
		this.minuteAdapter = new ShortCircuitAnnotationElementAdapter<Integer>(type, this.minuteDeclarationAdapter);
		this.secondDeclarationAdapter = buildSecondAdapter(daa);
		this.secondAdapter = new ShortCircuitAnnotationElementAdapter<Integer>(type, this.secondDeclarationAdapter);
		this.millisecondDeclarationAdapter = buildMillisecondAdapter(daa);
		this.millisecondAdapter = new ShortCircuitAnnotationElementAdapter<Integer>(type, this.millisecondDeclarationAdapter);
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

	public void update(CompilationUnit astRoot) {
		this.setHour(this.buildHour(astRoot));
		this.setMinute(this.buildMinute(astRoot));
		this.setSecond(this.buildSecond(astRoot));
		this.setMillisecond(this.buildMillisecond(astRoot));
	}


	// ********** TimeOfDayAnnotation implementation **********

	// ***** hour
	public Integer getHour() {
		return this.hour;
	}

	public void setHour(Integer hour) {
		if (this.attributeValueHasNotChanged(this.hour, hour)) {
			return;
		}
		Integer old = this.hour;
		this.hour = hour;
		this.hourAdapter.setValue(hour);
		this.firePropertyChanged(HOUR_PROPERTY, old, hour);
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

	public void setMinute(Integer newMinute) {
		if (attributeValueHasNotChanged(this.minute, newMinute)) {
			return;
		}
		Integer oldMinute = this.minute;
		this.minute = newMinute;
		this.minuteAdapter.setValue(newMinute);
		firePropertyChanged(MINUTE_PROPERTY, oldMinute, newMinute);
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
		if (attributeValueHasNotChanged(this.second, newSecond)) {
			return;
		}
		Integer oldSecond = this.second;
		this.second = newSecond;
		this.secondAdapter.setValue(newSecond);
		firePropertyChanged(SECOND_PROPERTY, oldSecond, newSecond);
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

	public void setMillisecond(Integer newMillisecond) {
		if (attributeValueHasNotChanged(this.millisecond, newMillisecond)) {
			return;
		}
		Integer oldMillisecond = this.millisecond;
		this.millisecond = newMillisecond;
		this.millisecondAdapter.setValue(newMillisecond);
		firePropertyChanged(MILLISECOND_PROPERTY, oldMillisecond, newMillisecond);
	}

	private Integer buildMillisecond(CompilationUnit astRoot) {
		return this.millisecondAdapter.getValue(astRoot);
	}

	public TextRange getMillisecondTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.millisecondDeclarationAdapter, astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<Integer> buildHourAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(daa, EclipseLinkJPA.TIME_OF_DAY__HOUR, false, NumberIntegerExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildMinuteAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(daa, EclipseLinkJPA.TIME_OF_DAY__MINUTE, false, NumberIntegerExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildSecondAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(daa, EclipseLinkJPA.TIME_OF_DAY__SECOND, false, NumberIntegerExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildMillisecondAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(daa, EclipseLinkJPA.TIME_OF_DAY__MILLISECOND, false, NumberIntegerExpressionConverter.instance());
	}

}
