/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.text.Collator;
import java.util.Comparator;
import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.details.MappingUiProvider;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

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
 * |                               ¯¯¯¯¯¯¯¯¯¯                                  |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class MapAsComposite<T extends Model> extends Pane<T> {

	protected boolean dragEvent;
	protected boolean enabled;
	protected Cursor handCursor;
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
	private static final String DIALOG_SETTINGS = "org.eclipse.jpt.ui.dialogs.MapAsDialog";

	/**
	 * Creates a new <code>MapAsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public MapAsComposite(Pane<? extends T> parentPane,
	                      Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates the default provider responsible for clearing the mapping type.
	 *
	 * @return A provider that acts as a default mapping provider
	 */
	protected abstract MappingUiProvider<?> getDefaultProvider();

	protected MappingUiProvider<?> getDefaultProvider(String mappingKey) {
		for (MappingUiProvider<?> provider : CollectionTools.iterable(this.mappingChangeHandler.defaultProviders())) {
			if (provider.getMappingKey().equals(mappingKey)) {
				return provider;
			}
		}
		return null;		
	}
	
	protected MappingUiProvider<?> getProvider(String mappingKey) {
		for (MappingUiProvider<?> provider : CollectionTools.iterable(this.mappingChangeHandler.providers())) {
			if (provider.getMappingKey() == mappingKey) {
				return provider;
			}
		}
		return null;		
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
						text.setCursor(handCursor);
					}
				}
				else if (isOverLink(offset)) {
					text.setCursor(handCursor);
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
					text.setCursor(handCursor);
				}
				else {
					text.setCursor(null);
				}
			}
		};
	}

	private PostExecution<MappingSelectionDialog> buildPostExecution() {

		return new PostExecution<MappingSelectionDialog>() {
			public void execute(MappingSelectionDialog dialog) {

				if (dialog.getReturnCode() == IDialogConstants.OK_ID) {
					MappingUiProvider<?> provider = (MappingUiProvider<?>) dialog.getFirstResult();
					morphMapping(provider);
				}
			}
		};
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

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		updateDescription();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {
		this.enabled = enabled;
		super.enableWidgets(enabled);

		if (!styledText.isDisposed()) {
			styledText.setEnabled(enabled);

			if (enabled) {
				updateLinkRange();
			}
			else {
				clearStyleRange();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {

		super.initialize();

		this.enabled = true;
		this.mappingChangeHandler = buildMappingChangeHandler();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		handCursor = getShell().getDisplay().getSystemCursor(SWT.CURSOR_HAND);

		styledText = new StyledText(container, SWT.WRAP | SWT.READ_ONLY);
		styledText.addMouseListener(buildMouseListener());
		styledText.addMouseMoveListener(buildMouseMoveListener());
		styledText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	/**
	 * Retreive the <code>MappingUiProvider</code> that provides the UI for the
	 * current mapping.
	 *
	 * @return The <code>MappingUiProvider</code> representing the type of the
	 * mapping being edited
	 */
	protected MappingUiProvider<?> initialSelection() {

		for (Iterator<? extends MappingUiProvider<?>> iter = mappingChangeHandler.providers(); iter.hasNext(); ) {
			MappingUiProvider<?> provider = iter.next();

			if (getMappingKey() == provider.getMappingKey()) {
				return provider;
			}
		}

		return null;
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
	 * Returns the mapping key representing the current mapping object.
	 *
	 * @return A non-<code>null</code> unique identifier representing the type
	 * of the mapping being edited
	 */
	protected abstract String getMappingKey();

	/**
	 * Aks the <code>MappingChangeHandler</code> to change the mapping type using
	 * the given <code>MappingUiProvider</code>.
	 *
	 * @param provider The provider used to determine the mapping type used for
	 * morphing the mapping being edited
	 */
	protected void morphMapping(MappingUiProvider<?> provider) {
		mappingChangeHandler.morphMapping(provider);
	}

	/**
	 * Opens the dialog that shows the registered mapping types in order for the
	 * user to select a provider in order to change the mapping type of the
	 * mapping being edited.
	 */
	protected void openMappingSelectionDialog() {

		MappingSelectionDialog dialog = new MappingSelectionDialog();
		SWTUtil.show(dialog, buildPostExecution());
	}

	/**
	 * Updates the description by recreating the label.
	 */
	protected void updateDescription() {

		clearStyleRange();
		updateText();

		if (enabled) {
			updateLinkRange();
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
			name = JptUiMappingsMessages.NoNameSet;
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
		 * @param provider The provider that was selected for changing the mapping
		 */
		void morphMapping(MappingUiProvider<?> provider);

		/**
		 * Returns the name of the current mapping.
		 *
		 * @return The displayable name of the mapping
		 */
		String getName();

		/**
		 * Returns the list of providers that are registered with the JPT plugin.
		 *
		 * @return The supported types of mapping
		 */
		Iterator<? extends MappingUiProvider<?>> providers();
		
		/**
		 * Returns the list of default providers that are registered with the JPT plugin.
		 *
		 * @return The supported types of mapping
		 */
		Iterator<? extends MappingUiProvider<?>> defaultProviders();
	}

	/**
	 * This dialog shows the list of possible mapping types and lets the user
	 * the option to filter them using a search field.
	 */
	protected class MappingSelectionDialog extends FilteredItemsSelectionDialog {

		private MappingUiProvider<?> defaultProvider;

		/**
		 * Creates a new <code>MappingSelectionDialog</code>.
		 */
		private MappingSelectionDialog() {
			super(MapAsComposite.this.getShell(), false);
			setMessage(JptUiMappingsMessages.MapAsComposite_labelText);
			setTitle(JptUiMappingsMessages.MapAsComposite_dialogTitle);
			setListLabelProvider(buildLabelProvider());
			setDetailsLabelProvider(buildLabelProvider());
		}

		private ILabelProvider buildLabelProvider() {
			return new LabelProvider() {

				@Override
				public Image getImage(Object element) {

					if (element == null) {
						return null;
					}

					MappingUiProvider<?> provider = (MappingUiProvider<?>) element;
					return provider.getImage();
				}

				@Override
				public String getText(Object element) {

					if (element == null) {
						return "";
					}

					MappingUiProvider<?> provider = (MappingUiProvider<?>) element;
					return provider.getLabel();
				}
			};
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected Control createExtendedContentArea(Composite parent) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected ItemsFilter createFilter() {
			return new MappingTypeItemsFilter();
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected void fillContentProvider(AbstractContentProvider provider,
		                                   ItemsFilter itemsFilter,
		                                   IProgressMonitor monitor) throws CoreException {

			monitor.beginTask(null, -1);

			try {
				// Add the default provider
				defaultProvider = getDefaultProvider();

				if (defaultProvider != null) {
					provider.add(defaultProvider, itemsFilter);
				}

				// Add the registered mapping providers to the dialog
				for (Iterator<? extends MappingUiProvider<?>> iter = mappingChangeHandler.providers(); iter.hasNext(); ) {
					MappingUiProvider<?> mappingProvider = iter.next();
					provider.add(mappingProvider, itemsFilter);
				}
			}
			finally {
				monitor.done();
			}
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected IDialogSettings getDialogSettings() {

			IDialogSettings dialogSettings = JptUiPlugin.getPlugin().getDialogSettings();
			IDialogSettings settings = dialogSettings.getSection(DIALOG_SETTINGS);

			if (settings == null) {
				settings = dialogSettings.addNewSection(DIALOG_SETTINGS);
			}

			return settings;
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		public String getElementName(Object object) {
			MappingUiProvider<?> provider = (MappingUiProvider<?>) object;
			return provider.getLabel();
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected Comparator<MappingUiProvider<?>> getItemsComparator() {
			return new Comparator<MappingUiProvider<?>>() {
				public int compare(MappingUiProvider<?> item1, MappingUiProvider<?> item2) {

					if (item1 == defaultProvider) {
						return -1;
					}

					if (item2 == defaultProvider) {
						return 1;
					}

					String displayString1 = item1.getLabel();
					String displayString2 = item2.getLabel();
					return Collator.getInstance().compare(displayString1, displayString2);
				}
			};
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected IStatus validateItem(Object item) {

			if (item == null) {
				return new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, IStatus.ERROR, "", null);
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
				if (StringTools.stringIsEmpty(getPattern())) {
					patternMatcher.setPattern("*");
				}
			}

			/*
			 * (non-Javadoc)
			 */
			@Override
			public boolean isConsistentItem(Object item) {
				return true;
			}

			/*
			 * (non-Javadoc)
			 */
			@Override
			public boolean matchItem(Object item) {
				MappingUiProvider<?> provider = (MappingUiProvider<?>) item;
				return matches(provider.getLabel());
			}
		}
	}
}
