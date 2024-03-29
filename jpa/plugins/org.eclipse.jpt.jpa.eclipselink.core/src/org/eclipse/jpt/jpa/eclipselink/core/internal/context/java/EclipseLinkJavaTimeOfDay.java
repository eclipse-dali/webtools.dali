/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTimeOfDay;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TimeOfDayAnnotation;

public class EclipseLinkJavaTimeOfDay
	extends AbstractJavaContextModel<EclipseLinkJavaCaching>
	implements EclipseLinkTimeOfDay
{
	protected final TimeOfDayAnnotation todAnnotation;

	protected Integer hour;
	protected Integer minute;
	protected Integer second;
	protected Integer millisecond;


	public EclipseLinkJavaTimeOfDay(EclipseLinkJavaCaching parent, TimeOfDayAnnotation todAnnotation) {
		super(parent);
		this.todAnnotation = todAnnotation;
		this.hour = todAnnotation.getHour();
		this.minute = todAnnotation.getMinute();
		this.second = todAnnotation.getSecond();
		this.millisecond = todAnnotation.getMillisecond();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setHour_(this.todAnnotation.getHour());
		this.setMinute_(this.todAnnotation.getMinute());
		this.setSecond_(this.todAnnotation.getSecond());
		this.setMillisecond_(this.todAnnotation.getMillisecond());
	}


	// ********** hour **********

	public Integer getHour() {
		return this.hour;
	}

	public void setHour(Integer hour) {
		this.todAnnotation.setHour(hour);
		this.setHour_(hour);
	}

	protected void setHour_(Integer hour) {
		Integer old = this.hour;
		this.hour = hour;
		this.firePropertyChanged(HOUR_PROPERTY, old, hour);
	}


	// ********** minute **********

	public Integer getMinute() {
		return this.minute;
	}

	public void setMinute(Integer minute) {
		this.todAnnotation.setMinute(minute);
		this.setMinute_(minute);
	}

	protected void setMinute_(Integer minute) {
		Integer old = this.minute;
		this.minute = minute;
		this.firePropertyChanged(MINUTE_PROPERTY, old, minute);
	}


	// ********** second **********

	public Integer getSecond() {
		return this.second;
	}

	public void setSecond(Integer second) {
		this.todAnnotation.setSecond(second);
		this.setSecond_(second);
	}

	protected void setSecond_(Integer second) {
		Integer old = this.second;
		this.second = second;
		this.firePropertyChanged(SECOND_PROPERTY, old, second);
	}


	// ********** millisecond **********

	public Integer getMillisecond() {
		return this.millisecond;
	}

	public void setMillisecond(Integer millisecond) {
		this.todAnnotation.setMillisecond(millisecond);
		this.setMillisecond_(millisecond);
	}

	protected void setMillisecond_(Integer millisecond) {
		Integer old = this.millisecond;
		this.millisecond = millisecond;
		this.firePropertyChanged(MILLISECOND_PROPERTY, old, millisecond);
	}


	// ********** misc **********

	public EclipseLinkJavaCaching getCaching() {
		return this.parent;
	}

	public TimeOfDayAnnotation getTimeOfDayAnnotation() {
		return this.todAnnotation;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.todAnnotation.getTextRange();
		return (textRange != null) ? textRange : this.getCaching().getValidationTextRange();
	}
}
