/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExpiryTimeOfDay;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;

/**
 * Here is the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Expiry -------------------------------------------------------------- | |
 * | |                                                                       | |
 * | | o No expiry                                                           | |
 * | |                     					----------------                 | |
 * | | o Time to live expiry   Expire after | I          |I| milliseconds    | |
 * | |                                      ----------------                 | |
 * | |                     				    --------------------             | |
 * | | o Daily expiry          Expire at    | HH:MM:SS:AM/PM |I|             | |
 * | |                                      --------------------             | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkCaching
 * @see EclipseLinkExpiryTimeOfDay
 * @see EclipseLinkCachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkExpiryComposite extends Pane<EclipseLinkCaching> {

	public EclipseLinkExpiryComposite(Pane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Expiry group pane
		Group expiryGroupPane = addTitledGroup(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkExpiryComposite_expirySection,
			2,
			null
		);
				
		// No Expiry radio button
		Button button = addRadioButton(
			expiryGroupPane,
			EclipseLinkUiDetailsMessages.EclipseLinkExpiryComposite_noExpiry,
			buildNoExpiryHolder(),
			null
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		button.setLayoutData(gridData);

		
		// Time To Live Expiry radio button
		addRadioButton(
			expiryGroupPane,
			EclipseLinkUiDetailsMessages.EclipseLinkExpiryComposite_timeToLiveExpiry,
			buildExpiryHolder(),
			null
		);
		
		addTimeToLiveComposite(expiryGroupPane);
		
		// Daily Expiry radio button
		addRadioButton(
			expiryGroupPane,
			EclipseLinkUiDetailsMessages.EclipseLinkExpiryComposite_dailyExpiry,
			buildTimeOfDayExpiryBooleanHolder(),
			null
		);
		
		addTimeOfDayComposite(expiryGroupPane);
	}
	
	protected void addTimeToLiveComposite(Composite parent) {
		Composite container = this.addSubPane(parent, 3, 0, 10, 0, 0);


		Control expireAfterLabel = addUnmanagedLabel(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkExpiryComposite_timeToLiveExpiryExpireAfter
		);
	
		IntegerCombo<?> combo = addTimeToLiveExpiryCombo(container);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		combo.getControl().setLayoutData(gridData);
		
		Control millisecondsLabel =	addUnmanagedLabel(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkExpiryComposite_timeToLiveExpiryMilliseconds
		);
		
		SWTTools.controlEnabledState(buildTimeToLiveExpiryEnabler(), expireAfterLabel, combo.getCombo(), millisecondsLabel);
	}
	
	protected void addTimeOfDayComposite(Composite parent) {
		Composite container = this.addSubPane(parent, 2, 0, 10, 0, 0);


		Control expireAtLabel = addUnmanagedLabel(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkExpiryComposite_timeOfDayExpiryExpireAt
		);
		
		PropertyValueModel<EclipseLinkExpiryTimeOfDay> timeOfDayExpiryHolder = buildTimeOfDayExpiryHolder();
		DateTime dateTime = addUnmanagedDateTime(
			container, 
			buildTimeOfDayExpiryHourHolder(timeOfDayExpiryHolder), 
			buildTimeOfDayExpiryMinuteHolder(timeOfDayExpiryHolder),
			buildTimeOfDayExpirySecondHolder(timeOfDayExpiryHolder),
			null);

		SWTTools.controlEnabledState(buildTimeOfDayExpiryEnabler(), expireAtLabel, dateTime);
	}
	
	private WritablePropertyValueModel<Boolean> buildNoExpiryHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
					getSubjectHolder(), 
					EclipseLinkCaching.EXPIRY_PROPERTY, 
					EclipseLinkCaching.EXPIRY_TIME_OF_DAY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getExpiry() == null && this.subject.getExpiryTimeOfDay() == null);
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setExpiry(null);
				if (this.subject.getExpiryTimeOfDay() != null) {
					this.subject.removeExpiryTimeOfDay();
				}
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildExpiryHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
					getSubjectHolder(), 
					EclipseLinkCaching.EXPIRY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getExpiry() != null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setExpiry(0);
				}
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildTimeOfDayExpiryBooleanHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
					getSubjectHolder(), 
					EclipseLinkCaching.EXPIRY_TIME_OF_DAY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getExpiryTimeOfDay() != null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.addExpiryTimeOfDay();
				}
			}
		};
	}
	
	private IntegerCombo<EclipseLinkCaching> addTimeToLiveExpiryCombo(Composite container) {
		return new IntegerCombo<EclipseLinkCaching>(this, container) {
		
			@Override
			protected CCombo addIntegerCombo(Composite container) {
				return this.addUnmanagedEditableCCombo(
						container,
						buildDefaultListHolder(),
						buildSelectedItemStringHolder(),
						StringConverter.Default.<String>instance());
			}
		
			@Override
			protected String getLabelText() {
				throw new UnsupportedOperationException();
			}
		
		
			@Override
			protected String getHelpId() {
				return null;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<EclipseLinkCaching, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return Integer.valueOf(0);
					}
				};
			}
			
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<EclipseLinkCaching, Integer>(getSubjectHolder(), EclipseLinkCaching.EXPIRY_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getExpiry();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setExpiry(value);
					}
				};
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildTimeToLiveExpiryEnabler() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.EXPIRY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getExpiry() != null);
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildTimeOfDayExpiryEnabler() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.EXPIRY_TIME_OF_DAY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getExpiryTimeOfDay() != null);
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkExpiryTimeOfDay> buildTimeOfDayExpiryHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, EclipseLinkExpiryTimeOfDay>(getSubjectHolder(), EclipseLinkCaching.EXPIRY_TIME_OF_DAY_PROPERTY) {
			@Override
			protected EclipseLinkExpiryTimeOfDay buildValue_() {
				return this.subject.getExpiryTimeOfDay();
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildTimeOfDayExpiryHourHolder(PropertyValueModel<EclipseLinkExpiryTimeOfDay> timeOfDayExpiryHolder) {
		return new PropertyAspectAdapter<EclipseLinkExpiryTimeOfDay, Integer>(
					timeOfDayExpiryHolder, 
					EclipseLinkExpiryTimeOfDay.HOUR_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return this.subject.getHour();
			}

			@Override
			protected void setValue_(Integer hour) {
				this.subject.setHour(hour);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildTimeOfDayExpiryMinuteHolder(PropertyValueModel<EclipseLinkExpiryTimeOfDay> timeOfDayExpiryHolder) {
		return new PropertyAspectAdapter<EclipseLinkExpiryTimeOfDay, Integer>(
					timeOfDayExpiryHolder, 
					EclipseLinkExpiryTimeOfDay.MINUTE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return this.subject.getMinute();
			}

			@Override
			protected void setValue_(Integer minute) {
				this.subject.setMinute(minute);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildTimeOfDayExpirySecondHolder(PropertyValueModel<EclipseLinkExpiryTimeOfDay> timeOfDayExpiryHolder) {
		return new PropertyAspectAdapter<EclipseLinkExpiryTimeOfDay, Integer>(
					timeOfDayExpiryHolder, 
					EclipseLinkExpiryTimeOfDay.SECOND_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return this.subject.getSecond();
			}

			@Override
			protected void setValue_(Integer second) {
				this.subject.setSecond(second);
			}
		};
	}

}
