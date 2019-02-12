/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.ExtendedCommandContext;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.reference.BooleanReference;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;

/**
 * The JPA project manager holds all the JPA projects in an Eclipse workspace
 * and provides support for executing long-running commands that modify the
 * context model.
 * <p>
 * Retrieve a JPA project manager from a {#link JpaWorkspace JPA workspace}.
 * <p>
 * Not intended to be implemented by clients.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JpaProjectManager
	extends Model
{
	/**
	 * Return the JPA project manager's JPA workspace.
	 */
	JpaWorkspace getJpaWorkspace();


	// ********** JPA projects **********

	/**
	 * Return the JPA project manager's JPA projects. This is a potentially
	 * long-running query (note the {@link InterruptedException}) as it will
	 * wait for any unbuilt JPA projects to be built or re-built.
	 * @see #getJpaProjects()
	 */
	Iterable<JpaProject> waitToGetJpaProjects() throws InterruptedException;

	/**
	 * Return the JPA project manager's JPA projects. The returned collection
	 * will not include any unbuilt or currently re-building JPA projects;
	 * but the client can listen for the appropriate events to be notified as
	 * JPA projects are added and/or removed from the collection.
	 * @see #addCollectionChangeListener(String, org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener)
	 * @see #JPA_PROJECTS_COLLECTION
	 * @see #waitToGetJpaProjects()
	 */
	Iterable<JpaProject> getJpaProjects();
		public static final String JPA_PROJECTS_COLLECTION = "jpaProjects"; //$NON-NLS-1$

	/**
	 * The size returned here corresponds to the collection returned by
	 * {@link #getJpaProjects()}.
	 */
	int getJpaProjectsSize();


	// ********** batch commands **********

	/**
	 * Suspend the current thread until the specified command is
	 * executed, configuring the JPA
	 * project manager appropriately so all events, JPA project updates, etc.
	 * are handled synchronously. The assumption is this method will be called
	 * when either the workspace is "headless" (non-UI) or none of the change
	 * events are fired from anywhere but the UI thread (e.g. Java Reconciler
	 * fires change events in a background thread).
	 * @see #execute(Command, ExtendedCommandContext)
	 */
	void execute(Command batchCommand) throws InterruptedException;

	/**
	 * Suspend the current thread until the specified command is
	 * executed, configuring the JPA
	 * project manager appropriately so all events, JPA project updates, etc.
	 * are handled synchronously. The assumption is this method will be called
	 * from within a job that may execute on a non-UI thread, necessitating a
	 * command context that will modify on the UI thread any documents that are
	 * currently open in the UI.
	 */
	void execute(Command command, ExtendedCommandContext threadLocalModifySharedDocumentCommandContext) throws InterruptedException;


	// ********** async event listener flags **********

	/**
	 * Add a flag that will be used to determine whether the manager's
	 * JDT java event listener is active:<ul>
	 * <li>If all the flags are <code>true</code>, the java listener
	 *     is active.
	 * <li>If <em>any</em> flag is <code>false</code>, the java listener
	 *     is <em><b>in</b></em>active
	 * </ul>
	 * This flag provides a way for clients to modify the context model directly
	 * without worrying about collisions caused by asynchronous JDT events.
	 * @see #execute(Command, ExtendedCommandContext)
	 *
	 */
	void addJavaEventListenerFlag(BooleanReference flag);

	/**
	 * Remove the specified flag.
	 * @see #addJavaEventListenerFlag(BooleanReference)
	 */
	void removeJavaEventListenerFlag(BooleanReference flag);


	// ********** async event listener flags **********

	/**
	 * The name of the JPA faceted project framework settings file.
	 * Listen for changes to this file to determine when the JPA facet is
	 * added to or removed from a "faceted" project.
	 * @see org.eclipse.jpt.common.core.internal.utility.ProjectTools#hasFacet(org.eclipse.core.resources.IProject, org.eclipse.wst.common.project.facet.core.IProjectFacet)
	 * @see JpaProject#FACET
	 */
	String FACETED_PROJECT_FRAMEWORK_SETTINGS_FILE_NAME = FacetedProjectFramework.PLUGIN_ID + ".xml"; //$NON-NLS-1$
}
