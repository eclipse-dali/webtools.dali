/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Comparator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.jface.ResourceManagerLabelProvider;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import com.ibm.icu.text.Collator;

/**
 * This map as composite simply shows a styled text where the name of the
 * mapping and its type are displayed. The mapping type can be clicked on to
 * invoke a dialog in order to change the type.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | Attribute 'name' is mapped as one to one.                                 |
 * |                               ----------                                  |
 * -----------------------------------------------------------------------------</pre>
 */
@SuppressWarnings("nls")
public abstract class MapAsComposite<T extends JpaStructureNode>
	extends Pane<T>
{
	protected boolean dragEvent;
	protected MappingChangeHandler mappingChangeHandler;
	protected int mappingTypeLength;
	protected int mappingTypeStart;
	protected boolean mouseDown;
	protected int nameLength;
	protected int nameStart;
	protected StyledText styledText;

	/**
	 * The constant ID used to retrieve the dialog settings.
	 */
	private static final String DIALOG_SETTINGS = "org.eclipse.jpt.jpa.ui.dialogs.MapAsDialog";


	protected MapAsComposite(Pane<? extends T> parentPane, Composite parentComposite) {
		super(parentPane, parentComposite);
	}

	protected MapAsComposite(Pane<? extends T> parentPane, Composite parent, PropertyValueModel<Boolean> enabledModel) {
		super(parentPane, parent, enabledModel);
	}
	
	/**
	 * Returns the JPT platform responsble to manage the user interface part of
	 * the JPT plug-in.
	 *
	 * @return The UI platform of the JPT plug-in
	 */
	protected JpaPlatformUi getJpaPlatformUi() {
		return (JpaPlatformUi) getSubject().getJpaProject().getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}

	/**
	 * Creates the default provider responsible for clearing the mapping type.
	 * Return null if there is not a default provider
	 * @return A provider that acts as a default mapping provider
	 */
	protected abstract DefaultMappingUiDefinition getDefaultDefinition();

	protected abstract DefaultMappingUiDefinition getDefaultDefinition(String mappingKey);
	
	protected MappingUiDefinition getMappingUiDefinition() {
		return this.mappingChangeHandler.getMappingUiDefinition();
	}
	
	/**
	 * Creates the handler responsible to give the information required for
	 * completing the behavior of this pane.
	 *
	 * @return A new <code>MappingChangeHandler</code>
	 */
	protected abstract MappingChangeHandler buildMappingChangeHandler();
	
	private MouseListener buildMouseListener() {
		return new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {
			}
			
			public void mouseDown(MouseEvent e) {
				if (e.button == 1) {
					mouseDown = true;
				}
			}

			public void mouseUp(MouseEvent e) {
				mouseDown = false;
				StyledText text = (StyledText) e.widget;
				int offset = text.getCaretOffset();

				if (dragEvent) {
					dragEvent = false;

					if (isOverLink(offset)) {
						text.setCursor(getHandCursor());
					}
				}
				else if (isOverLink(offset)) {
					text.setCursor(getHandCursor());
					openMappingSelectionDialog();
					text.setCursor(null);
				}
			}
		};
	}

	private MouseMoveListener buildMouseMoveListener() {
		return new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				StyledText text = (StyledText) e.widget;

				if (mouseDown) {
					if (!dragEvent) {
						text.setCursor(null);
					}

					dragEvent = true;
					return;
				}

				int offset = -1;

				try {
					offset = text.getOffsetAtLocation(new Point(e.x, e.y));
				}
				catch (IllegalArgumentException ex) {
				}

				if (isOverLink(offset)) {
					text.setCursor(getHandCursor());
				}
				else {
					text.setCursor(null);
				}
			}
		};
	}

	/**
	 * no need to store this since we are not supposed to dispose of it.
	 * @see Display#getSystemCursor(int)
	 */
	private Cursor getHandCursor() {
		return getShell().getDisplay().getSystemCursor(SWT.CURSOR_HAND);
	}

	/**
	 * Creates the full localized string by formatting the label text returned
	 * by the <code>MappingChangeHandler</code> with the mapping name and the
	 * mapping type.
	 *
	 * @param name The display string of the mapping being edited
	 * @param mappingType The localized message describing the mapping type
	 * @return The localized string describing the mapping
	 */
	protected String buildText(String name, String mappingType) {
		return NLS.bind(
			mappingChangeHandler.getLabelText(),
			name,
			mappingType
		);
	}

	/**
	 * Removes any style applied to the styled text.
	 */
	protected void clearStyleRange() {
		styledText.setStyleRange(null);
	}

	@Override
	protected void doPopulate() {
		super.doPopulate();
		updateDescription();
	}

	@Override
	protected void initialize() {

		super.initialize();
		this.mappingChangeHandler = buildMappingChangeHandler();
	}

	@Override
	protected void enabledModelChanged(boolean oldEnabled, boolean newEnabled) {
		if ( ! this.styledText.isDisposed()) {
			if (newEnabled) {
				this.updateLinkRange();
			} else {
				this.clearStyleRange();					
			}
		}
	}

	@Override
	protected Composite addComposite(Composite parent) {
		this.styledText = new StyledText(parent, SWT.WRAP | SWT.READ_ONLY);
		this.bindEnabledState(this.styledText);
		this.styledText.addMouseListener(buildMouseListener());
		this.styledText.addMouseMoveListener(buildMouseMoveListener());
		this.styledText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return this.styledText;
	}

	@Override
	public Composite getControl() {
		return (Composite) super.getControl();
	}

	/**
	 * @see #addComposite(Composite)
	 */
	@Override
	protected void initializeLayout(Composite container) {
		// NOP - code is in addComposite(...)
	}

	/**
	 * Retreive the <code>MappingUiDefinition</code> that provides the UI for the
	 * current mapping.
	 *
	 * @return The <code>MappingUiProvider</code> representing the type of the
	 * mapping being edited
	 */
	protected MappingUiDefinition initialSelection() {
		return this.mappingChangeHandler.getMappingUiDefinition();
	}

	/**
	 * Determines whether the given location is within the mapping type range.
	 *
	 * @param location The mouse location in character coordinate
	 * @return <code>true</code> if the mouse is over the mapping type text;
	 * <code>false</code> otherwise
	 */
	protected boolean isOverLink(int location) {

		return (location >= mappingTypeStart &&
		        location <= mappingTypeStart + mappingTypeLength);
	}

	/**
	 * Aks the <code>MappingChangeHandler</code> to change the mapping type using
	 * the given <code>MappingUiDefinition</code>.
	 *
	 * @param provider The provider used to determine the mapping type used for
	 * morphing the mapping being edited
	 */
	protected void morphMapping(MappingUiDefinition definition) {
		mappingChangeHandler.morphMapping(definition);
	}

	/**
	 * Opens the dialog that shows the registered mapping types in order for the
	 * user to select a provider in order to change the mapping type of the
	 * mapping being edited.
	 */
	protected void openMappingSelectionDialog() {

		MappingSelectionDialog dialog = new MappingSelectionDialog(this.getShell(), this.getResourceManager());
		dialog.setBlockOnOpen(true);
		if (dialog.open() == IDialogConstants.OK_ID) {
			MappingUiDefinition definition = (MappingUiDefinition) dialog.getFirstResult();
			morphMapping(definition);
		}
	}

	/**
	 * Updates the description by recreating the label.
	 */
	protected void updateDescription() {
		if (getSubject() == null) {
			return;
		}

		clearStyleRange();
		updateText();

		if (this.isEnabled()) {
			this.updateLinkRange();
		}
	}

	/**
	 * Updates the colors of the text: (1) the name is shown in bold and (2) the
	 * mapping type is shown in bold and in hyperlink color.
	 */
	protected void updateLinkRange() {

		Color linkColor = JFaceColors.getHyperlinkText(getShell().getDisplay());

		// Make the name bold
		StyleRange styleRange = new StyleRange(
			nameStart, nameLength,
			null, null,
			SWT.BOLD
		);
		styledText.setStyleRange(styleRange);

		// Make the mapping type shown as a hyperlink
		if (mappingTypeStart > -1) {
			styleRange = new StyleRange(
				mappingTypeStart, mappingTypeLength,
				linkColor, null
			);

			styleRange.underline      = true;
			styleRange.underlineColor = linkColor;
			styleRange.underlineStyle = SWT.UNDERLINE_SINGLE;
			styledText.setStyleRange(styleRange);
		}
	}

	/**
	 * Updates the styles text's input.
	 */
	protected void updateText() {

		String name = mappingChangeHandler.getName();

		if (name == null) {
			name = JptJpaUiDetailsMessages.NO_NAME_SET;
		}

		String mappingType = mappingChangeHandler.getMappingText();
		String text = buildText(name, mappingType);

		mappingTypeStart  = text.lastIndexOf(mappingType);
		mappingTypeLength = mappingType.length();

		nameStart  = text.indexOf(name);
		nameLength = name.length();

		styledText.setText(text);
	}

	/**
	 * This handler is responsible to give the text information and to open the
	 * mapping dialog if the user clicked on the mapping type.
	 */
	protected interface MappingChangeHandler {

		/**
		 * Returns the entire text describing the mapping (entity or mapping) and
		 * its type.
		 *
		 * @return A localized text with two arguments where the first one should
		 * be replaced by the name and the second be replaced by the mapping type
		 */
		String getLabelText();

		/**
		 * Returns the displayable text representing the mapping type.
		 *
		 * @return A human readable text describing the mapping type
		 */
		String getMappingText();

		/**
		 * Morphes the current mapping into a new type by using the given provider.
		 *
		 * @param provider The definition that was selected for changing the mapping
		 */
		void morphMapping(MappingUiDefinition definition);

		/**
		 * Returns the name of the current mapping.
		 *
		 * @return The displayable name of the mapping
		 */
		String getName();

		/**
		 * Returns the list of mapping UI definitions that are registered with the JpaPlatformUi.
		 *
		 * @return The supported types of mapping
		 */
		Iterable<MappingUiDefinition> getMappingUiDefinitions();

		/**
		 * Returns the mapping UI definition for current mapping
		 * that is registered with the JpaPlatformUi.
		 *
		 * @return The supported types of mapping
		 */
		MappingUiDefinition getMappingUiDefinition();
	}

	/**
	 * Lock the project while morphing the mapping to postpone any "updates"
	 * until we are finished; then set the JPA selection.
	 */
	public abstract class AbstractMappingChangeHandler
		implements MappingChangeHandler
	{
		/**
		 * @see org.eclipse.jpt.jpa.ui.internal.handlers.JpaStructureViewHandler#execute(org.eclipse.core.commands.ExecutionEvent)
		 */
		public final void morphMapping(MappingUiDefinition definition) {
			IProject project = MapAsComposite.this.getSubject().getJpaProject().getProject();
			try {
				Job.getJobManager().beginRule(project, null);
				this.morphMapping_(definition);
				this.setJpaSelection(MapAsComposite.this.getSubject());
			} finally {
				Job.getJobManager().endRule(project);
			}
		}

		protected abstract void morphMapping_(MappingUiDefinition definition);

		/**
		 * @see org.eclipse.jpt.jpa.ui.internal.handlers.JpaStructureViewHandler#setJpaSelection(org.eclipse.ui.IWorkbenchWindow, JpaStructureNode)
		 */
		private void setJpaSelection(JpaStructureNode jpaSelection) {
			JpaSelectionManager mgr = this.getJpaSelectionManager();
			mgr.setSelection(null);
			mgr.setSelection(jpaSelection);
		}

		private JpaSelectionManager getJpaSelectionManager() {
			return WorkbenchTools.getAdapter(JpaSelectionManager.class);
		}
	}

	/**
	 * This dialog shows the list of possible mapping types and lets the user
	 * the option to filter them using a search field.
	 */
	protected class MappingSelectionDialog
		extends FilteredItemsSelectionDialog 
	{
		private MappingUiDefinition defaultDefinition;
		
		/**
		 * Creates a new <code>MappingSelectionDialog</code>.
		 */
		protected MappingSelectionDialog(Shell shell, ResourceManager resourceManager) {
			super(shell, false);
			this.setMessage(JptJpaUiDetailsMessages.MAP_AS_COMPOSITE_LABEL_TEXT);
			this.setTitle(JptJpaUiDetailsMessages.MAP_AS_COMPOSITE_DIALOG_TITLE);
			ILabelProvider labelProvider = this.buildLabelProvider(resourceManager);
			this.setListLabelProvider(labelProvider);
			this.setDetailsLabelProvider(labelProvider);
		}

		private ILabelProvider buildLabelProvider(ResourceManager resourceManager) {
			return new ResourceManagerLabelProvider<MappingUiDefinition>(
					MappingUiDefinition.IMAGE_DESCRIPTOR_TRANSFORMER,
					MappingUiDefinition.LABEL_TRANSFORMER,
					resourceManager
				);
		}

		@Override
		protected Control createExtendedContentArea(Composite parent) {
			return null;
		}

		@Override
		protected ItemsFilter createFilter() {
			return new MappingTypeItemsFilter();
		}

		@Override
		protected void fillContentProvider(
				AbstractContentProvider provider,
				ItemsFilter itemsFilter,
				IProgressMonitor monitor) throws CoreException {
			
			monitor.beginTask(null, -1);

			try {
				// Add the default provider
				this.defaultDefinition = getDefaultDefinition();

				if (defaultDefinition != null) {
					provider.add(defaultDefinition, itemsFilter);
				}

				// Add the registered mapping providers to the dialog
				for (MappingUiDefinition mappingDefinition : mappingChangeHandler.getMappingUiDefinitions()) {
					provider.add(mappingDefinition, itemsFilter);
				}
			}
			finally {
				monitor.done();
			}
		}

		@Override
		protected IDialogSettings getDialogSettings() {
			return JptJpaUiPlugin.instance().getDialogSettings(DIALOG_SETTINGS);
		}

		@Override
		public String getElementName(Object object) {
			MappingUiDefinition definition = (MappingUiDefinition) object;
			return definition.getLabel();
		}

		@Override
		protected Comparator<MappingUiDefinition> getItemsComparator() {
			return new Comparator<MappingUiDefinition>() {
				public int compare(MappingUiDefinition item1, MappingUiDefinition item2) {

					if (item1 == defaultDefinition) {
						return -1;
					}

					if (item2 == defaultDefinition) {
						return 1;
					}

					String displayString1 = item1.getLabel();
					String displayString2 = item2.getLabel();
					return Collator.getInstance().compare(displayString1, displayString2);
				}
			};
		}

		@Override
		protected IStatus validateItem(Object item) {

			if (item == null) {
				return JptJpaUiPlugin.instance().buildErrorStatus();
			}

			return Status.OK_STATUS;
		}

		/**
		 * Create the filter responsible to remove any mapping type based on the
		 * pattern entered in the text field.
		 */
		private class MappingTypeItemsFilter extends ItemsFilter {

			/**
			 * Creates a new <code>MappingTypeItemsFilter</code>.
			 */
			MappingTypeItemsFilter() {

				super();

				// Make sure that if the pattern is empty, we specify * in order
				// to show all the mapping types
				if (StringTools.isBlank(getPattern())) {
					patternMatcher.setPattern("*");
				}
			}

			@Override
			public boolean isConsistentItem(Object item) {
				return true;
			}

			@Override
			public boolean matchItem(Object item) {
				MappingUiDefinition definition = (MappingUiDefinition) item;
				return matches(definition.getLabel());
			}
		}
	}
}
