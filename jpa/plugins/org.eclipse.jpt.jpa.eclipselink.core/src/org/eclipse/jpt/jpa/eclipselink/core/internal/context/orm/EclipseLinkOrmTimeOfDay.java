/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTimeOfDay;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay;

public class EclipseLinkOrmTimeOfDay
	extends AbstractOrmXmlContextModel<EclipseLinkCaching>
	implements EclipseLinkTimeOfDay
{
	protected final XmlTimeOfDay xmlTimeOfDay;

	protected Integer hour;
	protected Integer minute;
	protected Integer second;
	protected Integer millisecond;


	public EclipseLinkOrmTimeOfDay(EclipseLinkCaching parent, XmlTimeOfDay xmlTimeOfDay) {
		super(parent);
		this.xmlTimeOfDay = xmlTimeOfDay;
		this.hour = xmlTimeOfDay.getHour();
		this.minute = xmlTimeOfDay.getMinute();
		this.second = xmlTimeOfDay.getSecond();
		this.millisecond = xmlTimeOfDay.getMillisecond();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setHour_(this.xmlTimeOfDay.getHour());
		this.setMinute_(this.xmlTimeOfDay.getMinute());
		this.setSecond_(this.xmlTimeOfDay.getSecond());
		this.setMillisecond_(this.xmlTimeOfDay.getMillisecond());
	}


	// ********** hour **********

	public Integer getHour() {
		return this.hour;
	}

	public void setHour(Integer hour) {
		this.setHour_(hour);
		this.xmlTimeOfDay.setHour(hour);
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
		this.setMinute_(minute);
		this.xmlTimeOfDay.setMinute(minute);
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
		this.setSecond_(second);
		this.xmlTimeOfDay.setSecond(second);
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
		this.setMillisecond_(millisecond);
		this.xmlTimeOfDay.setMillisecond(millisecond);
	}

	protected void setMillisecond_(Integer millisecond) {
		Integer old = this.millisecond;
		this.millisecond = millisecond;
		this.firePropertyChanged(MILLISECOND_PROPERTY, old, millisecond);
	}


	// ********** misc **********

	protected EclipseLinkCaching getCaching() {
		return this.parent;
	}

	public XmlTimeOfDay getXmlTimeOfDay() {
		return this.xmlTimeOfDay;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlTimeOfDay.getValidationTextRange();
		return (textRange != null) ? textRange : this.getCaching().getValidationTextRange();
	}
}
