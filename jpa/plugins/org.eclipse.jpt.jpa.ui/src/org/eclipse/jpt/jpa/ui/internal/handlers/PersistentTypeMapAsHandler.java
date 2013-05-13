/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import java.util.Map;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IEvaluationService;

/**
 * This handler changes the mapping type of the selected
 * {@link PersistentType}(s).
 * It will be invoked by the mapping action dynamically created by the
 * {@link org.eclipse.jpt.jpa.ui.internal.menus.PersistentTypeMapAsContribution}.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 * 
 * @see org.eclipse.jpt.jpa.ui.internal.menus.PersistentTypeMapAsContribution
 * @see PersistentType
 */
public class PersistentTypeMapAsHandler
	extends JpaStructureViewHandler
	implements IElementUpdater
{
	/**
	 * The unique identifier of the "Map As" command used for
	 * {@link PersistentType}(s).
	 * <p>
	 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
	 */
	public static final String COMMAND_ID = "org.eclipse.jpt.jpa.ui.persistentTypeMapAs"; //$NON-NLS-1$

	/**
	 * The unique identifier of the "Map As" command parameter used for
	 * {@link PersistentType}(s).
	 * <p>
	 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
	 */
	public static final String COMMAND_PARAMETER_ID = "persistentTypeMappingKey"; //$NON-NLS-1$


	/**
	 * Default constructor.
	 */
	public PersistentTypeMapAsHandler() {
		super();
	}

	@Override
	protected void execute_(Object[] items, Map<String, String> parameters, IWorkbenchWindow window) {
		String mappingKey = parameters.get(COMMAND_PARAMETER_ID);
		for (Object item : items) {
			PersistentType type = (PersistentType) item;
			type.setMappingKey(mappingKey);
		}
	}

	public void updateElement(UIElement element, @SuppressWarnings("rawtypes") Map parameters) {
		// Retrieve the selection for the UIElement

		// Due to Bug 226746, we have to use API workaround to retrieve current
		// selection
		IEvaluationService es
			= (IEvaluationService) element.getServiceLocator().getService(IEvaluationService.class);
		IViewPart part =
			(IViewPart) es.getCurrentState().getVariable(ISources.ACTIVE_PART_NAME);
		IStructuredSelection selection
			= (IStructuredSelection) part.getSite().getSelectionProvider().getSelection();

		String commonMappingKey = this.commonMappingKey(selection);

		String handlerMappingKey = (String) parameters.get(COMMAND_PARAMETER_ID);
		if (handlerMappingKey != null) {
			element.setChecked(handlerMappingKey.equals(commonMappingKey));
		}
	}

	protected String commonMappingKey(IStructuredSelection selection) {
		String commonKey = null;
		for (Object obj : selection.toArray()) {
			if ( ! (obj instanceof PersistentType)) {
				return null;
			}

			PersistentType persistentType = (PersistentType) obj;
			if (commonKey == null) {
				commonKey = persistentType.getMappingKey();
			}
			else if (! commonKey.equals(persistentType.getMappingKey())) {
				return null;
			}
		}
		return commonKey;
	}
}
