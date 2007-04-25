/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.mappings.DiscriminatorType;
import org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.db.internal.Connection;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.CComboViewer;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

//TODO repopulate this panel based on the Entity table changing
public class DiscriminatorColumnComposite extends BaseJpaComposite
{
	
	private IDiscriminatorColumn discriminatorColumn;
	private final Adapter discriminatorColumnListener;
	private ConnectionListener connectionListener;
			
	private CCombo columnCombo;
	private CComboViewer discriminatorTypeViewer;
	
	private ConnectionProfile connectionProfile;


	
	public DiscriminatorColumnComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
		this.discriminatorColumnListener = buildDiscriminatorColumnListener();
		this.connectionListener = buildConnectionListener();
	}
	
	private Adapter buildDiscriminatorColumnListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				discriminatorColumnChanged(notification);
			}
		};
	}
	
    private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {

			public void aboutToClose(Connection connection) {
				// not interested to this event.
			}

			public void closed(Connection connection) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						DiscriminatorColumnComposite.this.populateColumnCombo();
					}
				});
			}

			public void modified(Connection connection) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						DiscriminatorColumnComposite.this.populateColumnCombo();
					}
				});
			}

			public boolean okToClose(Connection connection) {
				// not interested to this event.
				return true;
			}

			public void opened(Connection connection) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						DiscriminatorColumnComposite.this.populateColumnCombo();
					}
				});
			}

			public void databaseChanged(Connection connection, final Database database) {
				return;
			}

			public void schemaChanged(Connection connection, final Schema schema) {
				return;
			}

			public void tableChanged(Connection connection, final Table table) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if(table == getDbTable()) {
							if (!getControl().isDisposed()) {
								DiscriminatorColumnComposite.this.populateColumnCombo();
							}
						}
					}
				});
			}
		};
    }

	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.numColumns = 2;
		composite.setLayout(layout);
				
		getWidgetFactory().createLabel(composite, JpaUiMappingsMessages.DiscriminatorColumnComposite_column);

		this.columnCombo = buildColumnCombo(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.columnCombo.setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.columnCombo, IJpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_COLUMN);
		
		getWidgetFactory().createLabel(composite, JpaUiMappingsMessages.DiscriminatorColumnComposite_discriminatorType);

		this.discriminatorTypeViewer = buildDiscriminatorTypeCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.discriminatorTypeViewer.getCombo().setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.discriminatorTypeViewer.getCombo(), IJpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_TYPE);
	}
	
	
	private CCombo buildColumnCombo(Composite parent) {
		final CCombo combo = getWidgetFactory().createCCombo(parent, SWT.FLAT);
  		combo.add(JpaUiMappingsMessages.ColumnComposite_defaultEmpty);
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}
				String tableText = ((CCombo) e.getSource()).getText();
				if (tableText.equals("")) { //$NON-NLS-1$
					tableText = null;
					if (discriminatorColumn.getSpecifiedName() == null || discriminatorColumn.getSpecifiedName().equals("")) { //$NON-NLS-1$
						return;
					}
				}
				
				if (tableText != null && combo.getItemCount() > 0 && tableText.equals(combo.getItem(0))) {
					tableText = null;
				}

				if (discriminatorColumn.getSpecifiedName() == null && tableText != null) {
					discriminatorColumn.setSpecifiedName(tableText);
				}
				if (discriminatorColumn.getSpecifiedName() != null && !discriminatorColumn.getSpecifiedName().equals(tableText)) {
					discriminatorColumn.setSpecifiedName(tableText);
				}
			}
		});
		return combo;
		
	}
	
	private CComboViewer buildDiscriminatorTypeCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		CComboViewer discriminatorTypeViewer = new CComboViewer(combo);
		discriminatorTypeViewer.add(DiscriminatorType.VALUES.toArray());
		
		discriminatorTypeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				DiscriminatorColumnComposite.this.discriminatorTypeSelectionChanged(event.getSelection());
			}
		});
		
		return discriminatorTypeViewer;
	}
	
	void discriminatorTypeSelectionChanged(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			DiscriminatorType discriminatorType = (DiscriminatorType) ((IStructuredSelection) selection).getFirstElement();
			if ( ! this.discriminatorColumn.getDiscriminatorType().equals(discriminatorType)) {
				this.discriminatorColumn.setDiscriminatorType(discriminatorType);
			}
		}
	}
	
	protected void discriminatorColumnChanged(Notification notification) {
		if (notification.getFeatureID(IDiscriminatorColumn.class) == JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__SPECIFIED_NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateColumnName();
				}
			});
		}
		else if (notification.getFeatureID(IDiscriminatorColumn.class) == JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DEFAULT_NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					populateDefaultColumnName();
				}
			});
		}

		else if (notification.getFeatureID(IDiscriminatorColumn.class) == JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE) {
			final DiscriminatorType discriminatorType = (DiscriminatorType) notification.getNewValue();
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (((StructuredSelection) discriminatorTypeViewer.getSelection()).getFirstElement() != discriminatorType) {
						discriminatorTypeViewer.setSelection(new StructuredSelection(discriminatorType));
					}					
				}
			});
		}
	}

	@Override
	protected void engageListeners() {
		if (this.discriminatorColumn != null) {
			this.discriminatorColumn.eAdapters().add(this.discriminatorColumnListener);
			this.addConnectionListener();
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.discriminatorColumn != null) {
			this.removeConnectionListener();
			this.discriminatorColumn.eAdapters().remove(this.discriminatorColumnListener);
		}
	}

	private ConnectionProfile getConnectionProfile() {
		if(this.connectionProfile == null) {
			IJpaProject jpaProject = this.discriminatorColumn.getJpaProject();
			this.connectionProfile = jpaProject.connectionProfile();
		}
		return this.connectionProfile;
	}
	
	private void addConnectionListener() {
		this.getConnectionProfile().addConnectionListener(this.connectionListener);
	}
	
	private void removeConnectionListener() {
		this.getConnectionProfile().removeConnectionListener(this.connectionListener);
	}

	private Table getDbTable() {
		return this.discriminatorColumn.dbTable();
	}
	
	private void populateColumnCombo() {
		//TODO don't do instanceof check here - check on Table, or isRoot check on Entity
		//this.tableCombo.setEnabled(!(this.table instanceof SingleTableInheritanceChildTableImpl));
		populateDefaultColumnName();

		if (this.getConnectionProfile().isConnected()) {
			this.columnCombo.remove(1, this.columnCombo.getItemCount()-1);
			Table table = getDbTable();
			if (table != null) {
				for (Iterator i = CollectionTools.sort(CollectionTools.list(table.columnNames())).iterator(); i.hasNext();) {
					this.columnCombo.add((String) i.next());
				}
			}
		}
		else {
			this.columnCombo.remove(1, this.columnCombo.getItemCount()-1);
		}
		populateColumnName();
	}
	
	protected void populateDefaultColumnName() {
		String defaultTableName = discriminatorColumn.getDefaultName();
		int selectionIndex = columnCombo.getSelectionIndex();
		columnCombo.setItem(0, NLS.bind(JpaUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultTableName));
		if (selectionIndex == 0) {
			//combo text does not update when switching between 2 mappings of the same type
			//that both have a default column name.  clear the selection and then set it again
			columnCombo.clearSelection();
			columnCombo.select(0);
		}		
	}
	
	protected void populateColumnName() {
		String tableName = this.discriminatorColumn.getSpecifiedName();
		String defaultName = this.discriminatorColumn.getDefaultName();
		if (tableName != null) {
			if (!this.columnCombo.getText().equals(tableName)) {
				this.columnCombo.setText(tableName);
			}
		}
		else {
			if (!this.columnCombo.getText().equals(NLS.bind(JpaUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultName))) {
				this.columnCombo.select(0);
			}
		}
	}
	public void doPopulate(EObject obj) {
		this.discriminatorColumn = (IDiscriminatorColumn) obj;
		if (this.discriminatorColumn != null) {
			populateColumnCombo();
			popuplateDiscriminatorTypeComboViewer();			
		}
	}
	
	public void doPopulate() {
		if (this.discriminatorColumn != null) {
			populateColumnCombo();
		}
	}
	
	private void popuplateDiscriminatorTypeComboViewer() {
		if (this.discriminatorColumn.getDiscriminatorType() == DiscriminatorType.DEFAULT) {
			if (((StructuredSelection) this.discriminatorTypeViewer.getSelection()).getFirstElement() != DiscriminatorType.DEFAULT) {
				this.discriminatorTypeViewer.setSelection(new StructuredSelection(DiscriminatorType.DEFAULT));
			}
		}
		else if (this.discriminatorColumn.getDiscriminatorType() == DiscriminatorType.CHAR) {
			if (((StructuredSelection) this.discriminatorTypeViewer.getSelection()).getFirstElement() != DiscriminatorType.CHAR) {
				this.discriminatorTypeViewer.setSelection(new StructuredSelection(DiscriminatorType.CHAR));
			}
		}
		else if (this.discriminatorColumn.getDiscriminatorType() == DiscriminatorType.INTEGER) {
			if (((StructuredSelection) this.discriminatorTypeViewer.getSelection()).getFirstElement() != DiscriminatorType.INTEGER) {
				this.discriminatorTypeViewer.setSelection(new StructuredSelection(DiscriminatorType.INTEGER));
			}
		}
		else {
			if (((StructuredSelection) this.discriminatorTypeViewer.getSelection()).getFirstElement() != DiscriminatorType.STRING) {
				this.discriminatorTypeViewer.setSelection(new StructuredSelection(DiscriminatorType.STRING));
			}
		}		
	}
}
