/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.validation;

import java.util.Iterator;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jpt.core.JpaModel;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.operations.IWorkbenchContext;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IProjectValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidatorJob;

/**
 * This class is referenced in the JPA extension for the
 * WTP validator extension point.
 */
public class JpaValidator implements IValidatorJob {


	// ********** IValidator implementation **********

	public void validate(IValidationContext context, IReporter reporter) throws ValidationException {
		reporter.removeAllMessages(this);

		for (Iterator<IMessage> stream = this.validationMessages(context); stream.hasNext(); ) {
			reporter.addMessage(this, stream.next());
		}
	}

	private Iterator<IMessage> validationMessages(IValidationContext context) {
		IProject project = ((IProjectValidationContext) context).getProject();
		return JptCorePlugin.getJpaProject(project).validationMessages();
	}

	public void cleanup(IReporter reporter) {
		// nothing to do
	}


	// ********** IValidatorJob implementation **********

	public ISchedulingRule getSchedulingRule(IValidationContext context) {
		// don't know what to return here.  my guess is that we want to return
		// the resource that is possibly being changed during our validation,
		// and since many resources in the project may be changed during this
		// validation, returning the project makes the most sense.
		return ((IWorkbenchContext) context).getProject();
	}

	public IStatus validateInJob(IValidationContext context, IReporter reporter) throws ValidationException {
		if (reporter.isCancelled()) {
			return Status.CANCEL_STATUS;
		}
		this.validate(context, reporter);
		return OK_STATUS;
	}


	// ********** marker clean-up **********

	private static final CollectionChangeListener JPA_MODEL_LISTENER = new LocalCollectionChangeListener();

	static {
		JptCorePlugin.getJpaModel().addCollectionChangeListener(JpaModel.JPA_PROJECTS_COLLECTION, JPA_MODEL_LISTENER);
	}

	/**
	 * When a JPA project is removed this listener will schedule a job to
	 * remove all the markers associated with the JPA project.
	 */
	private static class LocalCollectionChangeListener implements CollectionChangeListener {

		LocalCollectionChangeListener() {
			super();
		}

		public void itemsAdded(CollectionChangeEvent event) {
			// ignore
		}

		/**
		 * For now, we expect JPA projects to be removed one at a time.
		 */
		public void itemsRemoved(CollectionChangeEvent event) {
			@SuppressWarnings("unchecked")
			Iterator<JpaProject> items = (Iterator<JpaProject>) event.items();
			Job j = new DeleteMarkersJob(items.next());
			j.schedule();
			if (items.hasNext()) {
				throw new UnsupportedOperationException("unexpected event");
			}
		}

		public void collectionCleared(CollectionChangeEvent event) {
			throw new UnsupportedOperationException("unexpected event");
		}

		public void collectionChanged(CollectionChangeEvent event) {
			throw new UnsupportedOperationException("unexpected event");
		}

	}

	/**
	 * Delete all the markers associated with the specified JPA project.
	 */
	private static class DeleteMarkersJob extends Job {
		private final JpaProject jpaProject;

		DeleteMarkersJob(JpaProject jpaProject) {
			super("Delete Markers");
			this.jpaProject = jpaProject;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				IMarker[] markers = this.jpaProject.getProject().findMarkers(JptCorePlugin.VALIDATION_MARKER_ID, true, IResource.DEPTH_INFINITE);
				ResourcesPlugin.getWorkspace().deleteMarkers(markers);
			} catch (CoreException ex) {
				JptCorePlugin.log(ex);  // not much else we can do
			}
			return Status.OK_STATUS;
		}

	}

}
