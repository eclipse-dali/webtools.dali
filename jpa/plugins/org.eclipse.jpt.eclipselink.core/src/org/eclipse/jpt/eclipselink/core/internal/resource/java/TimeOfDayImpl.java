/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractResourceAnnotation;
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
import org.eclipse.jpt.eclipselink.core.resource.java.TimeOfDayAnnotation;


public class TimeOfDayImpl extends AbstractResourceAnnotation<Type> implements TimeOfDayAnnotation
{	
	private final AnnotationElementAdapter<Integer> hourAdapter;
	private final AnnotationElementAdapter<Integer> minuteAdapter;
	private final AnnotationElementAdapter<Integer> secondAdapter;
	private final AnnotationElementAdapter<Integer> millisecondAdapter;
	
	private final DeclarationAnnotationElementAdapter<Integer> hourDeclarationAdapter;
	private final DeclarationAnnotationElementAdapter<Integer> minuteDeclarationAdapter;
	private final DeclarationAnnotationElementAdapter<Integer> secondDeclarationAdapter;
	private final DeclarationAnnotationElementAdapter<Integer> millisecondDeclarationAdapter;
	
	
	private Integer hour;
	private Integer minute;
	private Integer second;
	private Integer millisecond;
	
	protected TimeOfDayImpl(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter daa) {
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
	
	//*************** TimeOfDayAnnotation implementation ****************
	
	public Integer getHour() {
		return this.hour;
	}
	
	public void setHour(Integer newHour) {
		if (attributeValueHasNotChanged(this.hour, newHour)) {
			return;
		}
		Integer oldHour = this.hour;
		this.hour = newHour;
		this.hourAdapter.setValue(newHour);
		firePropertyChanged(HOUR_PROPERTY, oldHour, newHour);
	}

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
	
	public TextRange getHourTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.hourDeclarationAdapter, astRoot);
	}

	public TextRange getMinuteTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.minuteDeclarationAdapter, astRoot);
	}
	
	public TextRange getSecondTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.secondDeclarationAdapter, astRoot);
	}
	
	public TextRange getMillisecondTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.millisecondDeclarationAdapter, astRoot);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.hour = this.hour(astRoot);
		this.minute = this.minute(astRoot);
		this.second = this.second(astRoot);
		this.millisecond = this.millisecond(astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setHour(this.hour(astRoot));
		this.setMinute(this.minute(astRoot));
		this.setSecond(this.second(astRoot));
		this.setMillisecond(this.millisecond(astRoot));
	}
	
	protected Integer hour(CompilationUnit astRoot) {
		return this.hourAdapter.getValue(astRoot);
	}
	
	protected Integer minute(CompilationUnit astRoot) {
		return this.minuteAdapter.getValue(astRoot);
	}
	
	protected Integer second(CompilationUnit astRoot) {
		return this.secondAdapter.getValue(astRoot);
	}
	
	protected Integer millisecond(CompilationUnit astRoot) {
		return this.millisecondAdapter.getValue(astRoot);
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
