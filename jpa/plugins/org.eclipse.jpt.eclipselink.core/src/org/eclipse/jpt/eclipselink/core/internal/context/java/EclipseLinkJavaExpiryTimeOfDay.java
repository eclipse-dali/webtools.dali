/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTimeOfDayAnnotation;

public class EclipseLinkJavaExpiryTimeOfDay extends AbstractJavaJpaContextNode
	implements EclipseLinkExpiryTimeOfDay
{
	
	protected Integer hour;
	protected Integer minute;
	protected Integer second;
	protected Integer millisecond;
	
	protected EclipseLinkTimeOfDayAnnotation timeOfDay;
	
	public EclipseLinkJavaExpiryTimeOfDay(EclipseLinkCaching parent) {
		super(parent);
	}
	
	public Integer getHour() {
		return this.hour;
	}

	public void setHour(Integer newHour) {
		Integer oldHour = this.hour;
		this.hour = newHour;
		this.timeOfDay.setHour(newHour);
		firePropertyChanged(HOUR_PROPERTY, oldHour, newHour);
	}

	protected void setHour_(Integer newHour) {
		Integer oldHour = this.hour;
		this.hour = newHour;
		firePropertyChanged(HOUR_PROPERTY, oldHour, newHour);
	}

	public Integer getMinute() {
		return this.minute;
	}

	public void setMinute(Integer newMinute) {
		Integer oldMinute = this.minute;
		this.minute = newMinute;
		this.timeOfDay.setMinute(newMinute);
		firePropertyChanged(MINUTE_PROPERTY, oldMinute, newMinute);
	}

	protected void setMinute_(Integer newMinute) {
		Integer oldMinute = this.minute;
		this.minute = newMinute;
		firePropertyChanged(MINUTE_PROPERTY, oldMinute, newMinute);
	}

	public Integer getSecond() {
		return this.second;
	}

	public void setSecond(Integer newSecond) {
		Integer oldSecond = this.second;
		this.second = newSecond;
		this.timeOfDay.setSecond(newSecond);
		firePropertyChanged(SECOND_PROPERTY, oldSecond, newSecond);
	}
	
	protected void setSecond_(Integer newSecond) {
		Integer oldSecond = this.second;
		this.second = newSecond;
		firePropertyChanged(SECOND_PROPERTY, oldSecond, newSecond);
	}

	public Integer getMillisecond() {
		return this.millisecond;
	}

	public void setMillisecond(Integer newMillisecond) {
		Integer oldMillisecond = this.millisecond;
		this.millisecond = newMillisecond;
		this.timeOfDay.setMillisecond(newMillisecond);
		firePropertyChanged(MILLISECOND_PROPERTY, oldMillisecond, newMillisecond);
	}
	
	protected void setMillisecond_(Integer newMillisecond) {
		Integer oldMillisecond = this.millisecond;
		this.millisecond = newMillisecond;
		firePropertyChanged(MILLISECOND_PROPERTY, oldMillisecond, newMillisecond);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.timeOfDay.getTextRange(astRoot);
	}
	
	public void initialize(EclipseLinkTimeOfDayAnnotation timeOfDay) {
		this.timeOfDay = timeOfDay;
		this.hour = getHour(timeOfDay);
		this.minute = getMinute(timeOfDay);
		this.second = getSecond(timeOfDay);
		this.millisecond = getMillisecond(timeOfDay);
	}
	
	public void update(EclipseLinkTimeOfDayAnnotation timeOfDay) {
		this.timeOfDay = timeOfDay;
		this.setHour_(getHour(timeOfDay));
		this.setMinute_(getMinute(timeOfDay));
		this.setSecond_(getSecond(timeOfDay));
		this.setMillisecond_(getMillisecond(timeOfDay));
	}
	
	protected Integer getHour(EclipseLinkTimeOfDayAnnotation timeOfDay) {
		return timeOfDay.getHour();
	}
	
	protected Integer getMinute(EclipseLinkTimeOfDayAnnotation timeOfDay) {
		return timeOfDay.getMinute();
	}
	
	protected Integer getSecond(EclipseLinkTimeOfDayAnnotation timeOfDay) {
		return timeOfDay.getSecond();
	}
	
	protected Integer getMillisecond(EclipseLinkTimeOfDayAnnotation timeOfDay) {
		return timeOfDay.getMillisecond();
	}
}
