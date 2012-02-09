/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveTablePane;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringConverter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Name:      |                                                             ||
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkConverter
 * @see EclipseLinkConvertComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkObjectTypeConverterComposite extends Pane<EclipseLinkObjectTypeConverter>
{

	/**
	 * Creates a new <code>ObjectTypeConverterComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkObjectTypeConverterComposite(PropertyValueModel<? extends EclipseLinkObjectTypeConverter> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		
		addLabeledText(
			container, 
			EclipseLinkUiDetailsMessages.EclipseLinkConverterComposite_nameTextLabel, 
			buildNameTextHolder());
		
		addDataTypeChooser(container);
		addObjectTypeChooser(container);
		
		addConversionValuesTable(container);

		addLabeledEditableCombo(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_defaultObjectValueLabel,
			buildDefaultObjectValueListHolder(),
			buildDefaultObjectValueHolder(),
			buildStringConverter(),
			null
		);

		new PaneEnabler(buildBooleanHolder(), this);
	}
	
	protected WritablePropertyValueModel<String> buildNameTextHolder() {
		return new PropertyAspectAdapter<EclipseLinkObjectTypeConverter, String>(
				getSubjectHolder(), JpaNamedContextNode.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getName();
			}
		
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setName(value);
			}
		};
	}
	
	private ClassChooserPane<EclipseLinkObjectTypeConverter> addDataTypeChooser(Composite container) {

		return new ClassChooserPane<EclipseLinkObjectTypeConverter>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkObjectTypeConverter, String>(getSubjectHolder(), EclipseLinkObjectTypeConverter.DATA_TYPE_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getDataType();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						this.subject.setDataType(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getDataType();
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_dataTypeLabel;
			}

			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				getSubject().setDataType(className);
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getEnclosingTypeSeparator();
			}

			@Override
			protected String getFullyQualifiedClassName() {
				return getSubject().getFullyQualifiedDataType();
			}
		};
	}

	private ClassChooserPane<EclipseLinkObjectTypeConverter> addObjectTypeChooser(Composite container) {

		return new ClassChooserPane<EclipseLinkObjectTypeConverter>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkObjectTypeConverter, String>(getSubjectHolder(), EclipseLinkObjectTypeConverter.OBJECT_TYPE_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getObjectType();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						this.subject.setObjectType(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getObjectType();
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_objectTypeLabel;
			}

			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				getSubject().setObjectType(className);
			}
			
			@Override
			protected int getTypeDialogStyle() {
				return IJavaElementSearchConstants.CONSIDER_CLASSES_AND_ENUMS;
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getEnclosingTypeSeparator();
			}

			@Override
			protected String getFullyQualifiedClassName() {
				return getSubject().getFullyQualifiedObjectType();
			}
		};
	}
	
	protected void addConversionValuesTable(Composite container) {
		
		// Join Columns group pane
		Group conversionValuesGroupPane = addTitledGroup(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_conversionValuesGroupTitle
		);

		WritablePropertyValueModel<EclipseLinkConversionValue> conversionValueHolder =
			buildConversionValueHolder();
		// Conversion Values add/remove list pane
		new AddRemoveTablePane<EclipseLinkObjectTypeConverter>(
			this,
			conversionValuesGroupPane,
			buildConversionValuesAdapter(),
			buildConversionValuesListModel(),
			conversionValueHolder,
			buildConversionValuesLabelProvider(),
			null//TODO need a help context id for this
		) {
			@Override
			protected ColumnAdapter<EclipseLinkConversionValue> buildColumnAdapter() {
				return new ConversionValueColumnAdapter();
			}
		};

	}

	protected WritablePropertyValueModel<EclipseLinkConversionValue> buildConversionValueHolder() {
		return new SimplePropertyValueModel<EclipseLinkConversionValue>();
	}

	protected AddRemoveListPane.Adapter buildConversionValuesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				EclipseLinkConversionValueDialog dialog = buildConversionValueDialogForAdd();
				addConversionValueFromDialog(dialog, listSelectionModel);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_conversionValueEdit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				EclipseLinkConversionValue conversionValue = (EclipseLinkConversionValue) listSelectionModel.selectedValue();
				EclipseLinkConversionValueDialog dialog = new EclipseLinkConversionValueDialog(getShell(), getSubject(), conversionValue);
				editConversionValueFromDialog(dialog, conversionValue);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				EclipseLinkObjectTypeConverter converter = getSubject();
				int[] selectedIndices = listSelectionModel.selectedIndices();

				for (int index = selectedIndices.length; --index >= 0; ) {
					converter.removeConversionValue(selectedIndices[index]);
				}
			}
		};
	}


	protected EclipseLinkConversionValueDialog buildConversionValueDialogForAdd() {
		return new EclipseLinkConversionValueDialog(getShell(), getSubject());
	}

	protected void addConversionValueFromDialog(EclipseLinkConversionValueDialog dialog, ObjectListSelectionModel listSelectionModel) {
		if (dialog.open() != Window.OK) {
			return;
		}

		EclipseLinkConversionValue conversionValue = this.getSubject().addConversionValue();
		conversionValue.setDataValue(dialog.getDataValue());
		conversionValue.setObjectValue(dialog.getObjectValue());

		listSelectionModel.setSelectedValue(conversionValue);
	}

	protected void editConversionValueFromDialog(EclipseLinkConversionValueDialog dialog, EclipseLinkConversionValue conversionValue) {
		if (dialog.open() != Window.OK) {
			return;
		}

		conversionValue.setDataValue(dialog.getDataValue());
		conversionValue.setObjectValue(dialog.getObjectValue());
	}
	
	private ListValueModel<EclipseLinkConversionValue> buildConversionValuesListModel() {
		return new ItemPropertyListValueModelAdapter<EclipseLinkConversionValue>(buildConversionValuesListHolder(), 
			EclipseLinkConversionValue.DATA_VALUE_PROPERTY,
			EclipseLinkConversionValue.OBJECT_VALUE_PROPERTY);
	}	

	private ListValueModel<EclipseLinkConversionValue> buildConversionValuesListHolder() {
		return new ListAspectAdapter<EclipseLinkObjectTypeConverter, EclipseLinkConversionValue>(getSubjectHolder(), EclipseLinkObjectTypeConverter.CONVERSION_VALUES_LIST) {
			@Override
			protected ListIterable<EclipseLinkConversionValue> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkConversionValue>(this.subject.getConversionValues());
			}

			@Override
			protected int size_() {
				return this.subject.getConversionValuesSize();
			}
		};
	}

	private ITableLabelProvider buildConversionValuesLabelProvider() {
		return new TableLabelProvider();
	}
	
	protected ListValueModel<String> buildDefaultObjectValueListHolder() {
		return new TransformationListValueModel<EclipseLinkConversionValue, String>(buildConversionValuesListModel()) {
			@Override
			protected String transformItem(EclipseLinkConversionValue conversionValue) {
				return conversionValue.getObjectValue();
			}
		};
	}
	
	protected WritablePropertyValueModel<String> buildDefaultObjectValueHolder() {
		return new PropertyAspectAdapter<EclipseLinkObjectTypeConverter, String>(
				getSubjectHolder(), EclipseLinkObjectTypeConverter.DEFAULT_OBJECT_VALUE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getDefaultObjectValue();
			}
		
			@Override
			protected void setValue_(String value) {
				if (value != null && value.length() == 0) {
					value = null;
				}
				this.subject.setDefaultObjectValue(value);
			}
		};
	}
	
	protected final StringConverter<String> buildStringConverter() {
		return new StringConverter<String>() {
			public String convertToString(String value) {
				return (value == null) ? "" : value; //$NON-NLS-1$
			}
		};
	}

	protected PropertyValueModel<Boolean> buildBooleanHolder() {
		return new TransformationPropertyValueModel<EclipseLinkObjectTypeConverter, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(EclipseLinkObjectTypeConverter value) {
				return Boolean.valueOf(value != null);
			}
		};
	}
	
	private class TableLabelProvider extends LabelProvider
		implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		
		public String getColumnText(Object element, int columnIndex) {
		
			EclipseLinkConversionValue conversionValue = (EclipseLinkConversionValue) element;
			String value = null;
			
			switch (columnIndex) {
				case ConversionValueColumnAdapter.DATA_VALUE_COLUMN: {
					value = conversionValue.getDataValue();
					break;
				}
			
				case ConversionValueColumnAdapter.OBJECT_VALUE_COLUMN: {
					value = conversionValue.getObjectValue();
					break;
				}
			}
			
			if (value == null) {
				value = ""; //$NON-NLS-1$
			}
		
			return value;
		}
	}

	private static class ConversionValueColumnAdapter implements ColumnAdapter<EclipseLinkConversionValue> {

		public static final int COLUMN_COUNT = 2;
		//public static final int SELECTION_COLUMN = 0;
		public static final int DATA_VALUE_COLUMN = 0;
		public static final int OBJECT_VALUE_COLUMN = 1;

		private WritablePropertyValueModel<String> buildDataValueHolder(EclipseLinkConversionValue subject) {
			return new PropertyAspectAdapter<EclipseLinkConversionValue, String>(EclipseLinkConversionValue.DATA_VALUE_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return this.subject.getDataValue();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setDataValue(value);
				}
			};
		}

		private WritablePropertyValueModel<String> buildObjectValueHolder(EclipseLinkConversionValue subject) {
			return new PropertyAspectAdapter<EclipseLinkConversionValue, String>(EclipseLinkConversionValue.OBJECT_VALUE_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return this.subject.getObjectValue();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setObjectValue(value);
				}
			};
		}

		public WritablePropertyValueModel<?>[] cellModels(EclipseLinkConversionValue subject) {
			WritablePropertyValueModel<?>[] holders = new WritablePropertyValueModel<?>[COLUMN_COUNT];
			//holders[SELECTION_COLUMN] = new SimplePropertyValueModel<Object>();
			holders[DATA_VALUE_COLUMN]      = buildDataValueHolder(subject);
			holders[OBJECT_VALUE_COLUMN]     = buildObjectValueHolder(subject);
			return holders;
		}

		public int columnCount() {
			return COLUMN_COUNT;
		}

		public String columnName(int columnIndex) {

			switch (columnIndex) {
				case ConversionValueColumnAdapter.DATA_VALUE_COLUMN: {
					return EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_conversionValuesDataValueColumn;
				}

				case ConversionValueColumnAdapter.OBJECT_VALUE_COLUMN: {
					return EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_conversionValuesObjectValueColumn;
				}

				default: {
					return null;
				}
			}
		}
	}
}
