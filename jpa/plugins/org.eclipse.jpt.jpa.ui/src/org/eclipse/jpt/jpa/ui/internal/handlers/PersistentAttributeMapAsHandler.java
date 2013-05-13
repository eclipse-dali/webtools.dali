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
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IEvaluationService;

/**
 * This handler changes the mapping type of the selected
 * {@link SpecifiedPersistentAttribute}(s).
 * It will be invoked by the mapping action dynamically created by the
 * {@link org.eclipse.jpt.jpa.ui.internal.menus.PersistentAttributeMapAsContribution}.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 * 
 * @see org.eclipse.jpt.jpa.ui.internal.menus.PersistentAttributeMapAsContribution
 * @see SpecifiedPersistentAttribute
 */
public class PersistentAttributeMapAsHandler
	extends JpaStructureViewHandler
	implements IElementUpdater
{
	/**
	 * The unique identifier of the "Map As" command used for
	 * {@link SpecifiedPersistentAttribute}(s).
	 * <p>
	 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
	 */
	public static final String COMMAND_ID = "org.eclipse.jpt.jpa.ui.persistentAttributeMapAs"; //$NON-NLS-1$

	/**
	 * The unique identifier of the "Map As" command parameter used for
	 * {@link SpecifiedPersistentAttribute}(s).
	 * <p>
	 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
	 */
	public static final String SPECIFIED_MAPPING_COMMAND_PARAMETER_ID = "specifiedPersistentAttributeMappingKey"; //$NON-NLS-1$

	public static final String DEFAULT_MAPPING_COMMAND_PARAMETER_ID = "defaultPersistentAttributeMappingKey"; //$NON-NLS-1$


	/**
	 * Default constructor.
	 */
	public PersistentAttributeMapAsHandler() {
		super();
	}

	@Override
	protected void execute_(Object[] items, Map<String, String> parameters, IWorkbenchWindow window) {
		String mappingKey = parameters.get(SPECIFIED_MAPPING_COMMAND_PARAMETER_ID);
		for (Object item : items) {
			SpecifiedPersistentAttribute attribute = (SpecifiedPersistentAttribute) item;
			attribute.setMappingKey(mappingKey);
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

		element.setChecked(this.selectedElementsMappingKeysMatch(selection, parameters));
	}

	//Check all the selected persistent attribute and verify that they have the same mapping type.
	//They must all be either default mappings or specified mappings as well.
	protected boolean selectedElementsMappingKeysMatch(IStructuredSelection selection, @SuppressWarnings("rawtypes") Map parameters) {
		String handlerSpecifiedMappingKey = (String) parameters.get(SPECIFIED_MAPPING_COMMAND_PARAMETER_ID);
		String handlerDefaultMappingKey = (String) parameters.get(DEFAULT_MAPPING_COMMAND_PARAMETER_ID);

		String commonDefaultKey = null;
		String commonSpecifiedKey = null;
		for (Object obj : selection.toArray()) {
			if (! (obj instanceof SpecifiedPersistentAttribute)) {
				//oddly enough, you have to check instanceof here, seems like a bug in the framework
				return false;
			}

			SpecifiedPersistentAttribute persistentAttribute = (SpecifiedPersistentAttribute) obj;
			if (persistentAttribute.getMapping().isDefault()) {
				if (commonSpecifiedKey != null) {
					return false;
				}
				if (commonDefaultKey == null) {
					commonDefaultKey = persistentAttribute.getMappingKey();
				}
				else if (!commonDefaultKey.equals(persistentAttribute.getMappingKey())) {
					return false;
				}
			}
			else {
				if (commonDefaultKey != null) {
					return false;
				}
				if (commonSpecifiedKey == null) {
					commonSpecifiedKey = persistentAttribute.getMappingKey();
				}
				else if (!commonSpecifiedKey.equals(persistentAttribute.getMappingKey())) {
					return false;
				}
			}
		}
		if (handlerSpecifiedMappingKey != null) {
			return handlerSpecifiedMappingKey.equals(commonSpecifiedKey);
		}
		else if (handlerDefaultMappingKey != null) {
			return handlerDefaultMappingKey.equals(commonDefaultKey);
		}
		return false;
	}
}
