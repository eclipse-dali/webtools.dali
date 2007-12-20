/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CascadeComposite extends BaseJpaComposite<IRelationshipMapping>
{
	private ICascade cascade;

	private Adapter relationshipMappingListener;
	private Adapter cascadeListener;


	private Button allCheckBox;
	private Button persistCheckBox;
	private Button mergeCheckBox;
	private Button removeCheckBox;
	private Button refreshCheckBox;

	public CascadeComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, widgetFactory);
		this.relationshipMappingListener = buildRelationshipMappingListener();
		this.cascadeListener = buildCascadeListener();
	}

	private Adapter buildRelationshipMappingListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				relationshipMappingChanged(notification);
			}
		};
	}

	private Adapter buildCascadeListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				cascadeChanged(notification);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite composite) {
		composite.setLayout(new FillLayout());

		Group cascadeGroup = getWidgetFactory().createGroup(composite, "Cascade");
		GridLayout layout = new GridLayout(5, false);
		cascadeGroup.setLayout(layout);

		GridData gridData;

		this.allCheckBox = createAllCheckBox(cascadeGroup);
		gridData = new GridData();
		this.allCheckBox.setLayoutData(gridData);

		this.persistCheckBox = createPersistCheckBox(cascadeGroup);
		gridData = new GridData();
		this.persistCheckBox.setLayoutData(gridData);

		this.mergeCheckBox = createMergeCheckBox(cascadeGroup);
		gridData = new GridData();
		this.mergeCheckBox.setLayoutData(gridData);

		this.removeCheckBox = createRemoveCheckBox(cascadeGroup);
		gridData = new GridData();
		this.removeCheckBox.setLayoutData(gridData);

		this.refreshCheckBox = createRefreshCheckBox(cascadeGroup);
		gridData = new GridData();
		this.refreshCheckBox.setLayoutData(gridData);
	}

	private Button createAllCheckBox(Composite composite) {
		Button button = getWidgetFactory().createButton(composite, "All", SWT.CHECK);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				CascadeComposite.this.allSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				CascadeComposite.this.allSelected(e);
			}
		});
		return button;
	}

	protected void allSelected(SelectionEvent e) {
		boolean setSelection = initializeCascade();
		if (setSelection) {
			this.cascade.setAll(this.allCheckBox.getSelection());
		}
	}

	private boolean initializeCascade() {
		if (allCheckBoxesFalse() && this.cascade != null) {
			disengageCascadeListener();
			this.relationshipMapping.setCascade(null);
			return false;
		}
		if (this.cascade == null) {
			this.cascade = this.relationshipMapping.createCascade();
			this.relationshipMapping.setCascade(this.cascade);
			engageCascadeListener();
		}
		return true;
	}

	private Button createPersistCheckBox(Composite composite) {
		Button button = getWidgetFactory().createButton(composite, "Persist", SWT.CHECK);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				CascadeComposite.this.persistSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				CascadeComposite.this.persistSelected(e);
			}
		});
		return button;
	}

	protected void persistSelected(SelectionEvent e) {
		boolean setSelection = initializeCascade();
		if (setSelection) {
			this.cascade.setPersist(this.persistCheckBox.getSelection());
		}
	}

	private Button createMergeCheckBox(Composite composite) {
		Button button = getWidgetFactory().createButton(composite, "Merge", SWT.CHECK);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				CascadeComposite.this.mergeSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				CascadeComposite.this.mergeSelected(e);
			}
		});
		return button;
	}

	protected void mergeSelected(SelectionEvent e) {
		boolean setSelection = initializeCascade();
		if (setSelection) {
			this.cascade.setMerge(this.mergeCheckBox.getSelection());
		}
	}

	private Button createRemoveCheckBox(Composite composite) {
		Button button = getWidgetFactory().createButton(composite, "Remove", SWT.CHECK);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				CascadeComposite.this.removeSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				CascadeComposite.this.removeSelected(e);
			}
		});
		return button;
	}

	protected void removeSelected(SelectionEvent e) {
		boolean setSelection = initializeCascade();
		if (setSelection) {
			this.cascade.setRemove(this.removeCheckBox.getSelection());
		}
	}

	private Button createRefreshCheckBox(Composite composite) {
		Button button = getWidgetFactory().createButton(composite, "Refresh", SWT.CHECK);
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				CascadeComposite.this.refreshSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				CascadeComposite.this.refreshSelected(e);
			}
		});
		return button;
	}

	protected void refreshSelected(SelectionEvent e) {
		boolean setSelection = initializeCascade();
		if (setSelection) {
			this.cascade.setRefresh(this.refreshCheckBox.getSelection());
		}
	}

	private boolean allCheckBoxesFalse() {
		return !(this.allCheckBox.getSelection()
				|| this.persistCheckBox.getSelection()
				|| this.mergeCheckBox.getSelection()
				|| this.removeCheckBox.getSelection()
				|| this.refreshCheckBox.getSelection());


	}

	private void relationshipMappingChanged(Notification notification) {
		if (notification.getFeatureID(IRelationshipMapping.class) ==
				JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING__CASCADE) {
			Display.getDefault().asyncExec(
				new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						if (CascadeComposite.this.cascade != null) {
							disengageCascadeListener();
						}
						CascadeComposite.this.cascade = CascadeComposite.this.relationshipMapping.getCascade();
						populateCascade();

						if (CascadeComposite.this.cascade != null) {
							engageCascadeListener();
						}
					}
				});
		}
	}

	private void cascadeChanged(final Notification notification) {
		switch (notification.getFeatureID(ICascade.class)) {
			case JpaCoreMappingsPackage.ICASCADE__ALL :
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							if (getControl().isDisposed()) {
								return;
							}
							CascadeComposite.this.allCheckBox.setSelection(notification.getNewBooleanValue());
						}
					});
				break;
			case JpaCoreMappingsPackage.ICASCADE__PERSIST :
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							if (getControl().isDisposed()) {
								return;
							}
							CascadeComposite.this.persistCheckBox.setSelection(notification.getNewBooleanValue());
						}
					});
				break;
			case JpaCoreMappingsPackage.ICASCADE__MERGE :
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							if (getControl().isDisposed()) {
								return;
							}
							CascadeComposite.this.mergeCheckBox.setSelection(notification.getNewBooleanValue());
						}
					});
				break;
			case JpaCoreMappingsPackage.ICASCADE__REMOVE :
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							if (getControl().isDisposed()) {
								return;
							}
							CascadeComposite.this.removeCheckBox.setSelection(notification.getNewBooleanValue());
						}
					});
				break;
			case JpaCoreMappingsPackage.ICASCADE__REFRESH :
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							if (getControl().isDisposed()) {
								return;
							}
							CascadeComposite.this.refreshCheckBox.setSelection(notification.getNewBooleanValue());
						}
					});
				break;
			default :
				break;
		}
		if (notification.getFeatureID(ICascade.class) ==
				JpaCoreMappingsPackage.ICASCADE__ALL) {
			Display.getDefault().asyncExec(
				new Runnable() {
					public void run() {
						if (getControl().isDisposed()) {
							return;
						}
						CascadeComposite.this.allCheckBox.setSelection(notification.getNewBooleanValue());
					}
				});
		}

	}


	@Override
	public void doPopulate() {
		if (this.subject() != null) {
			this.cascade = this.subject().getCascade();
			populateCascade();
		}
	}

	private void populateCascade() {
		if (this.cascade != null) {
			this.allCheckBox.setSelection(this.cascade.isAll());
			this.persistCheckBox.setSelection(this.cascade.isPersist());
			this.mergeCheckBox.setSelection(this.cascade.isMerge());
			this.removeCheckBox.setSelection(this.cascade.isRemove());
			this.refreshCheckBox.setSelection(this.cascade.isRefresh());
		}
		else {
			this.allCheckBox.setSelection(false);
			this.persistCheckBox.setSelection(false);
			this.mergeCheckBox.setSelection(false);
			this.removeCheckBox.setSelection(false);
			this.refreshCheckBox.setSelection(false);
		}
	}

	@Override
	protected void engageListeners() {
		if (this.subject() != null) {
			this.subject().eAdapters().add(this.relationshipMappingListener);
			if (this.subject() != null) {
				engageCascadeListener();
			}
		}
	}

	protected void engageCascadeListener() {
		this.cascade.eAdapters().add(this.cascadeListener);
	}

	protected void disengageCascadeListener() {
		this.cascade.eAdapters().remove(this.cascadeListener);
	}

	@Override
	protected void disengageListeners() {
		if (this.subject() != null) {
			this.subject().eAdapters().remove(this.relationshipMappingListener);
			if (this.cascade != null) {
				disengageCascadeListener();
			}
		}
	}
}