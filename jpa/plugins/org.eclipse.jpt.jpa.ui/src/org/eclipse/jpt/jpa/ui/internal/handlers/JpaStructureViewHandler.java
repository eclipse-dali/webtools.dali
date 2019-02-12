/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Lock the project to postpone any "updates" and set the JPA selection
 * afterwards.
 */
public abstract class JpaStructureViewHandler
	extends AbstractHandler
{
	/**
	 * Lock the project while executing to postpone any "updates"
	 * until we are finished; then set the JPA selection.
	 */
	public final Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		Object[] items = selection.toArray();
		if (items.length == 0) {
			return null;  // not sure this can happen...
		}

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IProject project = ((JpaStructureNode) items[0]).getJpaProject().getProject();
		@SuppressWarnings("unchecked")
		Map<String, String> parameters = event.getParameters();
		try {
			Job.getJobManager().beginRule(project, null);
			this.execute_(items, parameters, window);
		} finally {
			Job.getJobManager().endRule(project);
		}
		// re-get the first item as it might have been replaced by execute_(...)
		this.setJpaSelection(window, (JpaStructureNode) items[0]);
		return null;
	}

	protected abstract void execute_(Object[] items, Map<String, String> parameters, IWorkbenchWindow window);

	/**
	 * When we are changing an attribute mapping in the <code>orm.xml</code>
	 * file (by calling {@link SpecifiedPersistentAttribute#setMappingKey(String)},
	 * the following happens synchronously during the call:<ul>
	 * <li>The mapping (persistent attribute) is removed from the XML file.
	 * <li>The cursor moves to the empty position where the mapping used to be,
	 *     between the remaining, surrounding mappings, effectively changing the
	 *     JPA selection to the persistent type that contained the selected
	 *     persistent attribute.
	 * <li>The new mapping (persistent attribute) is added to the XML file in
	 *     the appropriate position, usually not at the same location as the old
	 *     mapping.
	 * </ul>
	 * At this point, the JPA selection is still the selected persistent
	 * attribute; but a text editor event has been fired (with a half-second
	 * delay - see
	 * {@link org.eclipse.jface.text.TextViewer#queuePostSelectionChanged(boolean)})
	 * that will change the JPA selection to the persistent type that contains
	 * the selected persistent attribute (as calculated from the current cursor
	 * position). We short-circuit this event by setting the JPA selection to
	 * <code>null</code> and back to the selected persistent attribute. We set
	 * the JPA selection to <code>null</code> and back because we
	 * must <em>change</em> the JPA selection (as opposed to simply re-setting
	 * it to the same persistent attribute) or no change event will be fired
	 * (since nothing changed). This double change should be invisible to the
	 * user....
	 */
	private void setJpaSelection(IWorkbenchWindow window, JpaStructureNode jpaSelection) {
		JpaSelectionManager mgr = PlatformTools.getAdapter(window, JpaSelectionManager.class);
		mgr.setSelection(null);
		mgr.setSelection(jpaSelection);
	}
}
