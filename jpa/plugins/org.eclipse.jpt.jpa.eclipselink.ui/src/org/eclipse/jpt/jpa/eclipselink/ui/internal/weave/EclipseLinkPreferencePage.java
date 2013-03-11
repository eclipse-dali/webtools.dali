/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.weave;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.common.core.internal.utility.ProjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.builder.EclipseLinkStaticWeavingBuilderConfigurator;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLoggingLevel;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class EclipseLinkPreferencePage extends PropertyPage {

	private EclipseLinkStaticWeavingBuilderConfigurator configurator;
	private StaticWeavingComposite staticWeavingComposite;
	
	// ********** constructors **********
	
	public EclipseLinkPreferencePage() {
		super();
		setDescription(JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_DESCRIPTION);
	}
	
	// ********** overrides **********

	@Override
	public boolean performOk() {
		super.performOk();

		if(ProjectTools.hasFacet(this.getProject(), JpaProject.FACET) && this.staticWeaveClasses()) {
			if( ! this.projectHasStaticWeavingBuilder()) {
				this.configurator.addBuilder();
			}
			this.updateProjectStaticWeavingPreferences();
		}
		else {
			this.configurator.removeBuilder();
			this.removeProjectStaticWeavingPreferences();
		}
		return true;
	}

	@Override
	protected Control createContents(Composite parent) {
		this.configurator = new EclipseLinkStaticWeavingBuilderConfigurator(this.getProject());
		
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		
		this.noDefaultAndApplyButton();
		this.staticWeavingComposite = new StaticWeavingComposite(composite);
		
		return composite;
	}
	
	// ********** preferences **********
	
	private void updateProjectStaticWeavingPreferences() {
		String location = StringTools.isBlank(this.getSourceFolder()) ? this.getDefaultSource() : this.getSourceFolder();
		this.configurator.setSourceLocationPreference(location);
		location = StringTools.isBlank(this.getTargetFolder()) ? this.getDefaultTarget() : this.getTargetFolder();
		this.configurator.setTargetLocationPreference(location);
		location = StringTools.isBlank(this.getPersistenceInfo()) ? this.getDefaultPersistenceInfo() : this.getPersistenceInfo();
		this.configurator.setPersistenceInfoPreference(location);
		this.configurator.setLogLevelPreference(this.getLogLevel());
	}

	private void removeProjectStaticWeavingPreferences() {

		this.configurator.removeSourceLocationPreference();
		this.configurator.removeTargetLocationPreference();
		this.configurator.removeLogLevelPreference();
		this.configurator.removePersistenceInfoPreference();
	}

	// ********** internal methods **********
	
	private boolean projectHasStaticWeavingBuilder() {
		return this.configurator.projectHasStaticWeavingBuilder();
	}

	private IProject getProject() {
        IAdaptable adaptable= this.getElement();
		return adaptable == null ? null : (IProject)adaptable.getAdapter(IProject.class);
	}

	private String makeRelativeToProjectPath(String filePath) {
		Path path = new Path(filePath);
		IPath relativePath = path.makeRelativeTo(this.getProject().getLocation());
		return relativePath.toOSString();
	}

	// ********** getters *********
	
	private boolean staticWeaveClasses() {
		return this.staticWeavingComposite.getStaticWeaveCheckBoxValue();
	}
	
	private String getSourceFolder() {
		return this.staticWeavingComposite.getSourceFolder();
	}
	
	private String getTargetFolder() {
		return this.staticWeavingComposite.getTargetFolder();
	}
	
	private String getPersistenceInfo() {
		return this.staticWeavingComposite.getPersistenceInfoFolder();
	}
	
	private String getLogLevel() {
		return this.staticWeavingComposite.getLogLevel();
	}
	
	// ********** queries *********
	
	private String getSourcePreference() {
		return this.configurator.getSourceLocationPreference();
	}
	
	private String getTargetPreference() {
		return this.configurator.getTargetLocationPreference();
	}
	
	private String getPersistenceInfoPreference() {
		return this.configurator.getPersistenceInfoPreference();
	}

	private String getLogLevelPreference() {
		return this.configurator.getLogLevelPreference();
	}

	private String getDefaultSource() {
		return this.configurator.getDefaultSourceLocation();
	}

	private String getDefaultTarget() {
		return this.configurator.getDefaultTargetLocation();
	}

	private String getDefaultPersistenceInfo() {
		return this.configurator.getDefaultPersistenceInfo();
	}

	private String getDefaultLogLevel() {
		return this.configurator.getDefaultLogLevel();
	}
	
	// ********** StaticWeavingComposite **********

	class StaticWeavingComposite {
		private final Button staticWeaveClassesCheckBox;
		private final Label sourceLabel;
		private final Text sourceFolderText;
		private final Button browseSourceButton;
		private final Label targetLabel;
		private final Text targetFolderText;
		private final Button browseTargetButton;
		private final Label persistenceInfoLabel;
		private final Text persistenceInfoText;
		private final Button browsePersistenceInfoButton;

		private final Label logLevelLabel;
		private final Combo logLevelComboBox;
		private String logLevel;

		// ********** constructor **********

		private StaticWeavingComposite(Composite parent) {
			super();
			Group weavingGroup = new Group(parent, SWT.NONE);
			GridLayout layout = new GridLayout(3, false);
			layout.verticalSpacing= convertVerticalDLUsToPixels(0);
			weavingGroup.setLayout(layout);
			weavingGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			weavingGroup.setText(JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_STATIC_WEAVING_GROUP_BOX);
			
			// checkbox
			this.staticWeaveClassesCheckBox = this.buildStaticWeaveCheckBox(weavingGroup, 
																	JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_WEAVE_CLASSES_ON_BUILD_LABEL, 
																	this.buildStaticWeaveCheckBoxListener(), 3);
			// source
			this.sourceLabel = this.buildLabel(weavingGroup, 1, JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_SOURCE_LABEL);
			this.sourceFolderText = this.buildText(weavingGroup, 1);
			this.browseSourceButton = this.buildBrowseButton(weavingGroup,
									this.buildBrowseSourceButtonSelectionListener(
										JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_SELECT_SOURCE_LABEL, 
										JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_SOURCE_FOLDER_SELECTION_LABEL));	
			// target
			this.targetLabel = this.buildLabel(weavingGroup, 1, JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_TARGET_LABEL);
			this.targetFolderText = this.buildText(weavingGroup, 1);
			this.browseTargetButton = this.buildBrowseButton(weavingGroup,
									this.buildBrowseTargetButtonSelectionListener(
										JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_SELECT_TARGET_LABEL, 
										JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_TARGET_FOLDER_SELECTION_LABEL));
			// log level combo-box
			this.logLevelLabel = this.buildLabel(weavingGroup, 1, JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_LOG_LEVEL_LABEL);
			this.logLevelComboBox = this.buildLogLevelComboBox(weavingGroup, 1);
			this.buildFiller(weavingGroup);
			// persistenceInfo
			this.persistenceInfoLabel = this.buildLabel(weavingGroup, 1, JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_PERSISTENCE_INFO_LABEL);
			this.persistenceInfoText = this.buildText(weavingGroup, 1);
			this.browsePersistenceInfoButton = this.buildBrowseButton(weavingGroup,
									this.buildBrowsePersistenceInfoButtonSelectionListener(
										JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_SELECT_PERSISTENCE_INFO_LABEL, 
										JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_PERSISTENCE_INFO_FOLDER_SELECTION_LABEL));

			// initialize staticWeave checkbox
			this.staticWeaveClassesCheckBox.setSelection(projectHasStaticWeavingBuilder());

			this.staticWeaveCheckBoxChanged();
		}
		
		// ********** listeners **********
		
		private SelectionListener buildStaticWeaveCheckBoxListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					staticWeaveCheckBoxChanged();
				}
			};
		}

		private void staticWeaveCheckBoxChanged() {
			boolean enabled = this.getStaticWeaveCheckBoxValue();

			this.setSourceEnabled(enabled);
			this.setTargetEnabled(enabled);
			this.setPersistenceInfoEnabled(enabled);
			this.setLogLevelEnabled(enabled);
			
			if(this.getStaticWeaveCheckBoxValue()) {
				this.initializeFromPreferences();
			}
			else {
				this.clearAll();
			}
		}

		private SelectionListener buildLogLevelComboBoxSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					// nothing special for "default" (double-click?)
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					StaticWeavingComposite.this.selectedLogLevelChanged();
				}
				@Override
				public String toString() {
					return "EclipselinkPreferencePage logLevel combo-box selection listener"; //$NON-NLS-1$
				}
			};
		}

		private void selectedLogLevelChanged() {
			this.logLevel = this.logLevelComboBox.getText();
		}

		// ********** UI controls **********

		private void initializeFromPreferences() {
			// source
			this.sourceFolderText.setText(getSourcePreference());
			// target
			this.targetFolderText.setText(getTargetPreference());
			// persistenceInfo
			String persistenceInfo = (getPersistenceInfoPreference() != null) ? getPersistenceInfoPreference() : ""; //$NON-NLS-1$
			this.persistenceInfoText.setText(persistenceInfo);
			// log level
			this.logLevel = getLogLevelPreference();
			this.logLevelComboBox.select(this.logLevelComboBox.indexOf(this.logLevel));
		}
		
		private void clearAll() {
			// source
			this.sourceFolderText.setText(""); //$NON-NLS-1$
			// target
			this.targetFolderText.setText(""); //$NON-NLS-1$
			// persistenceInfo
			this.persistenceInfoText.setText(""); //$NON-NLS-1$
			// log level
			this.logLevel = getDefaultLogLevel();
			this.logLevelComboBox.select(this.logLevelComboBox.indexOf(this.logLevel));
		}
		
		private void setSourceEnabled(boolean enabled) {
			this.sourceLabel.setEnabled(enabled);
			this.sourceFolderText.setEnabled(enabled);
			this.browseSourceButton.setEnabled(enabled);
		}
		
		private void setTargetEnabled(boolean enabled) {
			this.targetLabel.setEnabled(enabled);
			this.targetFolderText.setEnabled(enabled);
			this.browseTargetButton.setEnabled(enabled);
		}
		
		private void setPersistenceInfoEnabled(boolean enabled) {
			this.persistenceInfoLabel.setEnabled(enabled);
			this.persistenceInfoText.setEnabled(enabled);
			this.browsePersistenceInfoButton.setEnabled(enabled);
		}

		private void setLogLevelEnabled(boolean enabled) {
			this.logLevelLabel.setEnabled(enabled);
			this.logLevelComboBox.setEnabled(enabled);
		}
		
		private Button buildBrowseButton(Composite parent, SelectionListener selectionListener) {
			Composite buttonComposite = new Composite(parent, SWT.NULL);
			GridLayout buttonLayout = new GridLayout(1, false);
			buttonComposite.setLayout(buttonLayout);
			GridData gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.BEGINNING;
			buttonComposite.setLayoutData(gridData);

			// Browse buttons
			Button browseButton = new Button(buttonComposite, SWT.PUSH);
			browseButton.setText(JptJpaEclipseLinkUiMessages.ECLIPSELINK_PREFERENCE_PAGE_BROWSE);
			gridData = new GridData();
			gridData.horizontalAlignment= GridData.FILL;
			gridData.grabExcessHorizontalSpace= true;
			browseButton.setLayoutData(gridData);
			
			browseButton.addSelectionListener(selectionListener);
			return browseButton;
		}

		private SelectionListener buildBrowseSourceButtonSelectionListener(final String title, final String description) {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}
			
				public void widgetSelected(SelectionEvent e) {

					String directory = promptFolder(title, description, getSourceFolder());
					if ( ! StringTools.isBlank(directory)) {
						StaticWeavingComposite.this.sourceFolderText.setText(makeRelativeToProjectPath(directory));
					}
				}
			};
		}

		private SelectionListener buildBrowseTargetButtonSelectionListener(final String title, final String description) {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}
			
				public void widgetSelected(SelectionEvent e) {

					String directory = promptFolder(title, description, getTargetFolder());
					if ( ! StringTools.isBlank(directory)) {
						StaticWeavingComposite.this.targetFolderText.setText(makeRelativeToProjectPath(directory));
					}
				}
			};
		}

		private SelectionListener buildBrowsePersistenceInfoButtonSelectionListener(final String title, final String description) {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}
			
				public void widgetSelected(SelectionEvent e) {

					String directory = promptFolder(title, description, getPersistenceInfoFolder());
					if ( ! StringTools.isBlank(directory)) {
						StaticWeavingComposite.this.persistenceInfoText.setText(makeRelativeToProjectPath(directory));
					}
				}
			};
		}

		private Button buildStaticWeaveCheckBox(Composite parent, String text, SelectionListener listener, int horizontalSpan) {
			Button radioButton = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.horizontalSpan = horizontalSpan;
			radioButton.setLayoutData(gridData);
			radioButton.setText(text);
			radioButton.addSelectionListener(listener);
			return radioButton;
		}

		private Text buildText(Composite parent, int horizontalSpan) {
			Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
			GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gridData.horizontalSpan = horizontalSpan;
			text.setLayoutData(gridData);
			return text;
		}

		private Combo buildLogLevelComboBox(Composite parent, int horizontalSpan) {
			Combo combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
			GridData gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
			gridData.horizontalAlignment = SWT.FILL;
			gridData.horizontalSpan = horizontalSpan;
			gridData.grabExcessHorizontalSpace = true ;
			combo.setLayoutData(gridData);
			combo.addSelectionListener(this.buildLogLevelComboBoxSelectionListener());

			this.populateLogLevelComboBox(combo);
			return combo;
		}
		
		private void buildFiller(Composite parent) {
			new Label(parent, SWT.NULL);
		}
		
		private void populateLogLevelComboBox(Combo combo) {
			combo.removeAll();

			for (EclipseLinkLoggingLevel value : configurator.getLogLevelValues()) {
				combo.add(value.getPropertyValue());
			}
		}
		
		private Label buildLabel(Composite parent, int span, String text) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(text);
			GridData gridData = new GridData();
			gridData.horizontalSpan = span;
			label.setLayoutData(gridData);
			return label;
		}

		// ********** getters *********
		
		private String getSourceFolder() {
			return this.sourceFolderText.getText();
		}

		private String getTargetFolder() {
			return this.targetFolderText.getText();
		}

		private String getPersistenceInfoFolder() {
			return this.persistenceInfoText.getText();
		}
		
		private String getLogLevel() {
			return this.logLevel;
		}

		private boolean getStaticWeaveCheckBoxValue() {
			return this.staticWeaveClassesCheckBox.getSelection();
		}
		
		// ********** internal methods **********
		
		/**
		 * The browse button was clicked, its action invokes this action which should
		 * prompt the user to select a folder and set it.
		 */
		private String promptFolder(String title, String description, String relativeLocation) {

			DirectoryDialog dialog = new DirectoryDialog(getShell());
			dialog.setText(title);
			dialog.setMessage(description);
			dialog.setFilterPath(this.filterPath(relativeLocation));
			String directory = dialog.open();
			return directory;
		}
		
		protected String filterPath(String relativeLocation) {
			IPath location = getProject().getLocation(); 
			if( ! StringTools.isBlank(relativeLocation)) {
				location = location.append(relativeLocation);
			}
			return location.toPortableString();
		}
	}
	
}