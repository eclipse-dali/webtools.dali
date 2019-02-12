/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTimeOfDay;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

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
	                          Composite parent,
	                          PropertyValueModel<Boolean> enabledModel) {

		super(parentPane, parent, enabledModel);
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
	protected Composite addComposite(Composite container) {
		Group group = this.getWidgetFactory().createGroup(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXPIRY_COMPOSITE_EXPIRY_SECTION);

		GridLayout layout = new GridLayout(4, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 5;
		layout.marginLeft   = 5;
		layout.marginBottom = 5;
		layout.marginRight  = 5;
		group.setLayout(layout);

		return group;
	}

	@Override
	protected void initializeLayout(Composite container) {
		// No Expiry radio button
		Button button = addRadioButton(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXPIRY_COMPOSITE_NO_EXPIRY,
			buildNoExpiryHolder(),
			null
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 4;
		button.setLayoutData(gridData);

		
		// Time To Live Expiry radio button
		addRadioButton(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXPIRY_COMPOSITE_TIME_TO_LIVE_EXPIRY,
			buildExpiryHolder(),
			null
		);

		Label expireAfterLabel = this.addLabel(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXPIRY_COMPOSITE_TIME_TO_LIVE_EXPIRY_EXPIRE_AFTER,
			this.getTtlEnabled()
		);
		gridData = new GridData();
		gridData.horizontalIndent = 10;
		expireAfterLabel.setLayoutData(gridData);

		this.addTimeToLiveExpiryCombo(container);
		
		this.addLabel(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXPIRY_COMPOSITE_TIME_TO_LIVE_EXPIRY_MILLISECONDS,
			this.getTtlEnabled()
		);


		// Daily Expiry radio button
		addRadioButton(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXPIRY_COMPOSITE_DAILY_EXPIRY,
			buildTimeOfDayExpiryBooleanHolder(),
			null
		);

		PropertyValueModel<Boolean> todEnabled = this.buildTimeOfDayExpiryEnabler();
		Label expireAtLabel = addLabel(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXPIRY_COMPOSITE_TIME_OF_DAY_EXPIRY_EXPIRE_AT,
			todEnabled
		);
		gridData = new GridData();
		gridData.horizontalIndent = 10;
		expireAtLabel.setLayoutData(gridData);
		
		PropertyValueModel<EclipseLinkTimeOfDay> timeOfDayExpiryHolder = buildTimeOfDayExpiryHolder();
		DateTime dataTime = addDateTime(
			container, 
			buildTimeOfDayExpiryHourHolder(timeOfDayExpiryHolder), 
			buildTimeOfDayExpiryMinuteHolder(timeOfDayExpiryHolder),
			buildTimeOfDayExpirySecondHolder(timeOfDayExpiryHolder),
			null,
			todEnabled
		);
		
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		dataTime.setLayoutData(gridData);
	}
	
	private ModifiablePropertyValueModel<Boolean> buildNoExpiryHolder() {
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

	private ModifiablePropertyValueModel<Boolean> buildExpiryHolder() {
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
	
	private ModifiablePropertyValueModel<Boolean> buildTimeOfDayExpiryBooleanHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
					getSubjectHolder(), 
					EclipseLinkCaching.EXPIRY_TIME_OF_DAY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getExpiryTimeOfDay() != null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE && this.subject.getExpiryTimeOfDay() == null) {
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
						TransformerTools.<String>objectToStringTransformer(),
						EclipseLinkExpiryComposite.this.getTtlEnabled()
					);
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
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
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

	private ModifiablePropertyValueModel<Integer> buildTimeOfDayExpiryHourHolder(PropertyValueModel<EclipseLinkTimeOfDay> timeOfDayExpiryHolder) {
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

	private ModifiablePropertyValueModel<Integer> buildTimeOfDayExpiryMinuteHolder(PropertyValueModel<EclipseLinkTimeOfDay> timeOfDayExpiryHolder) {
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

	private ModifiablePropertyValueModel<Integer> buildTimeOfDayExpirySecondHolder(PropertyValueModel<EclipseLinkTimeOfDay> timeOfDayExpiryHolder) {
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
