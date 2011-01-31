/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTimeOfDayAnnotation;

/**
 * org.eclipse.persistence.annotations.TimeOfDay
 */
public final class BinaryEclipseLinkTimeOfDayAnnotation
	extends BinaryAnnotation
	implements EclipseLinkTimeOfDayAnnotation
{
	private Integer hour;
	private Integer minute;
	private Integer second;
	private Integer millisecond;


	public BinaryEclipseLinkTimeOfDayAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.hour = this.buildHour();
		this.minute = this.buildMinute();
		this.second = this.buildSecond();
		this.millisecond = this.buildMillisecond();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setHour_(this.buildHour());
		this.setMinute_(this.buildMinute());
		this.setSecond_(this.buildSecond());
		this.setMillisecond_(this.buildMillisecond());
	}


	// ********** TimeOfDayAnnotation implementation **********

	// ***** hour
	public Integer getHour() {
		return this.hour;
	}

	public void setHour(Integer hour) {
		throw new UnsupportedOperationException();
	}

	private void setHour_(Integer hour) {
		Integer old = this.hour;
		this.hour = hour;
		this.firePropertyChanged(HOUR_PROPERTY, old, hour);
	}

	private Integer buildHour() {
		return (Integer) this.getJdtMemberValue(EclipseLink.TIME_OF_DAY__HOUR);
	}

	public TextRange getHourTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** minute
	public Integer getMinute() {
		return this.minute;
	}

	public void setMinute(Integer newMinute) {
		throw new UnsupportedOperationException();
	}

	private void setMinute_(Integer newMinute) {
		Integer oldMinute = this.minute;
		this.minute = newMinute;
		this.firePropertyChanged(MINUTE_PROPERTY, oldMinute, newMinute);
	}

	private Integer buildMinute() {
		return (Integer) this.getJdtMemberValue(EclipseLink.TIME_OF_DAY__MINUTE);
	}

	public TextRange getMinuteTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** second
	public Integer getSecond() {
		return this.second;
	}

	public void setSecond(Integer newSecond) {
		throw new UnsupportedOperationException();
	}

	private void setSecond_(Integer newSecond) {
		Integer oldSecond = this.second;
		this.second = newSecond;
		this.firePropertyChanged(SECOND_PROPERTY, oldSecond, newSecond);
	}

	private Integer buildSecond() {
		return (Integer) this.getJdtMemberValue(EclipseLink.TIME_OF_DAY__SECOND);
	}

	public TextRange getSecondTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** millisecond
	public Integer getMillisecond() {
		return this.millisecond;
	}

	public void setMillisecond(Integer newMillisecond) {
		throw new UnsupportedOperationException();
	}

	private void setMillisecond_(Integer newMillisecond) {
		Integer oldMillisecond = this.millisecond;
		this.millisecond = newMillisecond;
		this.firePropertyChanged(MILLISECOND_PROPERTY, oldMillisecond, newMillisecond);
	}

	private Integer buildMillisecond() {
		return (Integer) this.getJdtMemberValue(EclipseLink.TIME_OF_DAY__MILLISECOND);
	}

	public TextRange getMillisecondTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
