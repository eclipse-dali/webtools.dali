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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTimeOfDay;
import org.eclipse.jpt.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
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
 * @see EclipseLinkTimeOfDay
 * @see EclipseLinkCachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkExpiryComposite extends Pane<EclipseLinkCaching> {
	protected PropertyValueModel<Boolean> ttlEnabled;

	public EclipseLinkExpiryComposite(Pane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * lazy init because we need it while the superclass constructor is
	 * executing
	 */
	protected PropertyValueModel<Boolean> getTtlEnabled() {
		if (this.ttlEnabled == null) {
			this.ttlEnabled = this.buildTimeToLiveExpiryEnabler();
		}
		return this.ttlEnabled;
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

		addLabel(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkExpiryComposite_timeToLiveExpiryExpireAfter,
			this.getTtlEnabled()
		);
	
		IntegerCombo<?> combo = addTimeToLiveExpiryCombo(container);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		combo.getControl().setLayoutData(gridData);
		
		addLabel(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkExpiryComposite_timeToLiveExpiryMilliseconds,
			this.getTtlEnabled()
		);
	}
	
	protected void addTimeOfDayComposite(Composite parent) {
		Composite container = this.addSubPane(parent, 2, 0, 10, 0, 0);

		PropertyValueModel<Boolean> todEnabled = this.buildTimeOfDayExpiryEnabler();
		addLabel(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkExpiryComposite_timeOfDayExpiryExpireAt,
			todEnabled
		);
		
		PropertyValueModel<EclipseLinkTimeOfDay> timeOfDayExpiryHolder = buildTimeOfDayExpiryHolder();
		addDateTime(
			container, 
			buildTimeOfDayExpiryHourHolder(timeOfDayExpiryHolder), 
			buildTimeOfDayExpiryMinuteHolder(timeOfDayExpiryHolder),
			buildTimeOfDayExpirySecondHolder(timeOfDayExpiryHolder),
			null,
			todEnabled
		);
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
					this.subject.setExpiry(Integer.valueOf(0));
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
			protected Combo addIntegerCombo(Composite container) {
				return this.addEditableCombo(
						container,
						buildDefaultListHolder(),
						buildSelectedItemStringHolder(),
						StringConverter.Default.<String>instance(),
						EclipseLinkExpiryComposite.this.getTtlEnabled()
					);
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
	
	private PropertyValueModel<EclipseLinkTimeOfDay> buildTimeOfDayExpiryHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, EclipseLinkTimeOfDay>(getSubjectHolder(), EclipseLinkCaching.EXPIRY_TIME_OF_DAY_PROPERTY) {
			@Override
			protected EclipseLinkTimeOfDay buildValue_() {
				return this.subject.getExpiryTimeOfDay();
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildTimeOfDayExpiryHourHolder(PropertyValueModel<EclipseLinkTimeOfDay> timeOfDayExpiryHolder) {
		return new PropertyAspectAdapter<EclipseLinkTimeOfDay, Integer>(
					timeOfDayExpiryHolder, 
					EclipseLinkTimeOfDay.HOUR_PROPERTY) {
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

	private WritablePropertyValueModel<Integer> buildTimeOfDayExpiryMinuteHolder(PropertyValueModel<EclipseLinkTimeOfDay> timeOfDayExpiryHolder) {
		return new PropertyAspectAdapter<EclipseLinkTimeOfDay, Integer>(
					timeOfDayExpiryHolder, 
					EclipseLinkTimeOfDay.MINUTE_PROPERTY) {
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

	private WritablePropertyValueModel<Integer> buildTimeOfDayExpirySecondHolder(PropertyValueModel<EclipseLinkTimeOfDay> timeOfDayExpiryHolder) {
		return new PropertyAspectAdapter<EclipseLinkTimeOfDay, Integer>(
					timeOfDayExpiryHolder, 
					EclipseLinkTimeOfDay.SECOND_PROPERTY) {
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
