/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * This class is responsible to set a preferred width on the registered widgets
 * (either <code>Control</code> or <code>ControlAligner</code>) based on the
 * widest widget.
 * <p>
 * Important: The layout data has to be a <code>GridData</code>. If none is set,
 * then a new <code>GridData</code> is automatically created.
 * <p>
 * Here an example of the result if this aligner is used to align controls
 * within either one or two group boxes, the controls added are the labels in
 * this case. It is also possible to align controls on the right side of the
 * main component, a spacer can be used for extra space.
 * <p>
 * Here's an example:
 * <pre>
 * - Group Box 1 --------------------------------------------------------------
 * |                     -------------------------------------- ------------- |
 * | Name:               | I                                  | | Browse... | |
 * |                     -------------------------------------- ------------- |
 * |                     ---------                                            |
 * | Preallocation Size: |     |I|                                            |
 * |                     ---------                                            |
 * |                     --------------------------------------               |
 * | Descriptor:         |                                  |v|               |
 * |                     --------------------------------------               |
 * ----------------------------------------------------------------------------
 *
 * - Group Box 2 --------------------------------------------------------------
 * |                     --------------------------------------               |
 * | Mapping Type:       |                                  |V|               |
 * |                     --------------------------------------               |
 * |                     --------------------------------------               |
 * | Check in Script:    | I                                  |               |
 * |                     --------------------------------------               |
 * ----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class ControlAligner
{
	/**
	 * Flag used to prevent a validation so it can be done after an operation
	 * completed.
	 */
	private boolean autoValidate;

	/**
	 * The utility class used to support bound properties.
	 */
	private Collection<Listener> changeSupport;

	/**
	 * The listener added to each of the controls that listens only to a text
	 * change.
	 */
	private Listener listener;

	/**
	 * Prevents infinite recursion when recalculating the preferred width.
	 * This happens in an hierarchy of <code>ControlAligner</code>s. The lock
	 * has to be placed here and not in the {@link ControlAlignerWrapper}.
	 */
	private boolean locked;

	/**
	 * The length of the widest control. If the length was not calculated, then
	 * this value is 0.
	 */
	private int maximumWidth;

	/**
	 * The collection of {@link Wrapper}s encapsulating either <code>Control</code>s
	 * or {@link ControlAligner}s.
	 */
	private Collection<Wrapper> wrappers;

	/**
	 * A null-<code>Point</code> object used to clear the preferred size.
	 */
	private static final Point DEFAULT_SIZE = new Point(SWT.DEFAULT, SWT.DEFAULT);

	/**
	 * The types of events to listen in order to properly adjust the size of all
	 * the widgets.
	 */
	private static final int[] EVENT_TYPES = {
		SWT.Dispose,
		SWT.Hide,
		SWT.Resize,
		SWT.Show
	};

	/**
	 * Creates a new <code>ControlAligner</code>.
	 */
	public ControlAligner() {
		super();
		initialize();
	}

	/**
	 * Creates a new <code>ControlAligner</code>.
	 *
	 * @param controls The collection of <code>Control</code>s
	 */
	public ControlAligner(Collection<? extends Control> controls) {
		this();
		addAllControls(controls);
	}

	/**
	 * Adds the given control. Its width will be used along with the width of all
	 * the other registered controls in order to get the greater witdh and use
	 * it as the width for all the controls.
	 *
	 * @param control The <code>Control</code> to be added
	 */
	public void add(Control control) {

		Assert.isNotNull(control, "Can't add null to this ControlAligner");

		Wrapper wrapper = buildWrapper(control);
		wrapper.addListener(listener);
		wrappers.add(wrapper);

		revalidate(false);
	}

	/**
	 * Adds the given control. Its width will be used along with the width of all
	 * the other registered controls in order to get the greater witdh and use
	 * it as the width for all the controls.
	 *
	 * @param controlAligner The <code>ControlAligner</code> to be added
	 * @exception IllegalArgumentException Can't add the <code>ControlAligner</code>
	 * to itself
	 */
	public void add(ControlAligner controlAligner) {

		Assert.isNotNull(controlAligner, "Can't add null to this ControlAligner");
		Assert.isLegal(controlAligner != this, "Can't add the ControlAligner to itself");

		Wrapper wrapper = buildWrapper(controlAligner);
		wrapper.addListener(listener);
		wrappers.add(wrapper);

		if (!controlAligner.wrappers.isEmpty()) {
			revalidate(false);
		}
	}

	/**
	 * Adds the items contained in the given collection into this
	 * <code>ControlAligner</code>. The preferred width of each item will be
	 * used along with the width of all the other items in order to get the
	 * widest control and use its width as the width for all the controls.
	 *
	 * @param aligners The collection of <code>ControlAligner</code>s
	 */
	public void addAllControlAligners(Collection<ControlAligner> aligners) {

		// Deactivate the auto validation while adding all the Controls and/or
		// ControlAligners in order to improve performance
		boolean oldAutoValidate = autoValidate;
		autoValidate = false;

		for (ControlAligner aligner : aligners) {
			add(aligner);
		}

		autoValidate = oldAutoValidate;
		revalidate(false);
	}

   /**
	 * Adds the items contained in the given collection into this
	 * <code>ControlAligner</code>. The preferred width of each item will be
	 * used along with the width of all the other items in order to get the
	 * widest control and use its width as the width for all the controls.
	 *
	 * @param controls The collection of <code>Control</code>s
	 */
	public void addAllControls(Collection<? extends Control> controls) {

		// Deactivate the auto validation while adding all the Controls and/or
		// ControlAligners in order to improve performance
		boolean oldAutoValidate = autoValidate;
		autoValidate = false;

		for (Control control : controls) {
			add(control);
		}

		autoValidate = oldAutoValidate;
		revalidate(false);
	}

   /**
	 * Adds the given <code>ControListener</code>.
	 *
	 * @param listener The <code>Listener</code> to be added
	 */
	private void addListener(Listener listener) {

		if (changeSupport == null) {
		    changeSupport = new ArrayList<Listener>();
		}

		changeSupport.add(listener);
   }

	/**
	 * Creates a new <code>Wrapper</code> that encapsulates the given source.
	 *
	 * @param control The control to be wrapped
	 * @return A new {@link Wrapper}
	 */
	private Wrapper buildWrapper(Control control) {
		return new ControlWrapper(control);
	}

   /**
	 * Creates a new <code>Wrapper</code> that encapsulates the given source.
	 *
	 * @param ControlAligner The <code>ControlAligner</code> to be wrapped
	 * @return A new {@link ControlAlignerWrapper}
	 */
	private Wrapper buildWrapper(ControlAligner ControlAligner) {
		return new ControlAlignerWrapper(ControlAligner);
	}

	/**
	 * Calculates the width taken by the widgets and returns the maximum width.
	 *
	 * @param recalculateSize <code>true</code> to recalculate the preferred size
	 * of all the wrappers contained within them rather than using the cached
	 * size; <code>false</code> to use the cached size
	 */
	private int calculateWidth(boolean recalculateSize) {

		int width = 0;

		for (Wrapper wrapper : wrappers) {
			Point size = wrapper.cachedSize();

			// The size has not been calculated yet
			if (recalculateSize || (size.x == 0)) {
				size = wrapper.calculateSize();
			}

			// Only keep the greatest width
			width = Math.max(size.x, width);
		}

		return width;
	}

	/**
	 * Reports a bound property change.
	 *
	 * @param oldValue the old value of the property (as an int)
	 * @param newValue the new value of the property (as an int)
	 */
	private void controlResized(int oldValue, int newValue) {

		if ((changeSupport != null) && (oldValue != newValue)) {
			Event event  = new Event();
			event.widget = SWTUtil.getShell();
			event.data   = this;

			for (Listener listener : changeSupport) {
				listener.handleEvent(event);
			}
		}
	}

	/**
	 * Disposes this <code>ControlAligner</code>, this can improve the speed of
	 * disposing a pane. When a pane is disposed, this aligner doesn't need to
	 * revalidate its size upon dispose of its widgets.
	 */
	public void dispose() {

		for (Iterator<Wrapper> iter = wrappers.iterator(); iter.hasNext(); ) {
			Wrapper wrapper = iter.next();
			wrapper.removeListener(listener);
			iter.remove();
		}

		this.wrappers.clear();
	}

	/**
	 * Returns the length of the widest control. If the length was not
	 * calculated, then this value is 0.
	 *
	 * @return The width of the widest control or 0 if the length has not been
	 * calculated yet
	 */
	public int getMaximumWidth() {
		return maximumWidth;
	}

	/**
	 * Initializes this <code>ControlAligner</code>.
	 */
	private void initialize() {

		this.autoValidate = true;
		this.maximumWidth = 0;
		this.listener     = new ListenerHandler();
		this.wrappers     = new ArrayList<Wrapper>();
	}

	/**
	 * Invalidates the size of the given object.
	 *
	 * @param source The source object to be invalidated
	 */
	private void invalidate(Object source) {

		Wrapper wrapper = retrieveWrapper(source);

		if (!wrapper.locked()) {
			Point size = wrapper.cachedSize();
			size.x = size.y = 0;
			wrapper.setSize(DEFAULT_SIZE);
		}
	}

	/**
	 * Updates the maximum length based on the widest control. This methods
	 * does not update the width of the controls.
	 *
	 * @param recalculateSize <code>true</code> to recalculate the preferred size
	 * of all the wrappers contained within them rather than using the cached
	 * size; <code>false</code> to use the cached size
	 */
	private void recalculateWidth(boolean recalculateSize) {

		int width = calculateWidth(recalculateSize);

		try {
			locked = true;
			setMaximumWidth(width);
		}
		finally {
			locked = false;
		}
	}

	/**
	 * Removes the given control. Its preferred width will not be used when
	 * calculating the preferred width.
	 *
	 * @param control The control to be removed
	 * @exception AssertionFailedException If the given <code>Control</code> is
	 * <code>null</code>
	 */
	public void remove(Control control) {

		Assert.isNotNull(control, "The Control to remove cannot be null");

		Wrapper wrapper = retrieveWrapper(control);
		wrapper.removeListener(listener);
		wrappers.remove(wrapper);

		revalidate(true);
	}

	/**
	 * Removes the given <code>ControlAligner</code>. Its preferred width
	 * will not be used when calculating the preferred witdh.
	 *
	 * @param controlAligner The <code>ControlAligner</code> to be removed
	 * @exception AssertionFailedException If the given <code>ControlAligner</code>
	 * is <code>null</code>
	 */
	public void remove(ControlAligner controlAligner) {

		Assert.isNotNull(controlAligner, "The ControlAligner to remove cannot be null");

		Wrapper wrapper = retrieveWrapper(controlAligner);
		wrapper.removeListener(listener);
		wrappers.remove(wrapper);

		revalidate(true);
	}

	/**
	 * Removes the given <code>Listener</code>.
	 *
	 * @param listener The <code>Listener</code> to be removed
	 */
	private void removeListener(Listener listener) {

		changeSupport.remove(listener);

		if (changeSupport.isEmpty()) {
			changeSupport = null;
		}
	}

	/**
	 * Retrieves the <code>Wrapper</code> that is encapsulating the given object.
	 *
	 * @param source Either a <code>Control</code> or a <code>ControlAligner</code>
	 * @return Its <code>Wrapper</code>
	 */
	private Wrapper retrieveWrapper(Object source) {

		for (Wrapper wrapper : wrappers) {
			if (wrapper.source() == source) {
				return wrapper;
			}
		}

		throw new IllegalArgumentException("Can't retrieve the Wrapper for " + source);
	}

	/**
	 * If the count of control is greater than one and {@link #isAutoValidate()}
	 * returns <code>true</code>, then the size of all the registered
	 * <code>Control</code>s will be udpated.
	 *
	 * @param recalculateSize <code>true</code> to recalculate the preferred size
	 * of all the wrappers contained within them rather than using the cached
	 * size; <code>false</code> to use the cached size
	 */
	private void revalidate(boolean recalculateSize) {

		if (autoValidate) {
			recalculateWidth(recalculateSize);
			updateWrapperSize(recalculateSize);
		}
	}

	/**
	 * Bases on the information contained in the given <code>Event</code>,
	 * resize the controls.
	 *
	 * @param event The <code>Event</code> sent by the UI thread when the state
	 * of a widget changed
	 */
	private void revalidate(Event event) {

		// We don't need to revalidate during a revalidation process
		if (locked) {
			return;
		}

		Object source;

		if (event.widget != SWTUtil.getShell()) {
			source = event.widget;
			Control control = (Control) source;

			// When a dialog is opened, we need to actually force a layout of
			// the controls, this is required because the control is actually
			// not visible when the preferred width is caculated
			if (control == control.getShell()) {
				if (event.type == SWT.Dispose) {
					return;
				}

				source = null;
			}
		}
		else {
			source = event.data;
		}

		// Either remove the ControlWrapper if the widget was disposed or
		// invalidate the widget in order to recalculate the preferred size
		if (source != null) {
			if (event.type == SWT.Dispose) {
				Wrapper wrapper = retrieveWrapper(source);
				wrappers.remove(wrapper);
			}
			else {
				invalidate(source);
			}
		}

		// Now revalidate all the Controls and ControlAligners
		revalidate(true);
	}

	/**
	 * Sets the length of the widest control. If the length was not calulcated,
	 * then this value is 0.
	 *
	 * @param maximumWidth The width of the widest control
	 */
	private void setMaximumWidth(int maximumWidth) {

		int oldMaximumWidth = this.maximumWidth;
		this.maximumWidth = maximumWidth;
		controlResized(oldMaximumWidth, maximumWidth);
	}

	/**
	 * Returns a string representation of this <code>ControlAligner</code>.
	 *
	 * @return Information about this object
	 */
	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("maximumWidth=");
		sb.append(maximumWidth);
		sb.append(", wrappers=");
		sb.append(wrappers);
		return StringTools.buildToStringFor(this, sb);
	}

	/**
	 * Updates the size of every <code>Wrapper</code> based on the maximum width.
	 *
	 * @param forceRevalidate <code>true</code> to revalidate the wrapper's size
	 * even though its current size might be the same as the maximum width;
	 * <code>false</code> to only revalidate the wrappers with a different width
	 */
	private void updateWrapperSize(boolean forceRevalidate) {

		for (Wrapper wrapper : wrappers) {
			Point cachedSize = wrapper.cachedSize();

			// No need to change the size of the wrapper since it's always using
			// the maximum width
			if (forceRevalidate || (cachedSize.x != maximumWidth)) {
				Point size = new Point(maximumWidth, cachedSize.y);
				wrapper.setSize(size);
			}
		}
	}

	/**
	 * This <code>Wrapper</code> encapsulates a {@link ControlAligner}.
	 */
	private class ControlAlignerWrapper implements Wrapper {
		/**
		 * The cached size, which is {@link ControlAligner#maximumWidth}.
		 */
		private final Point cachedSize;

		/**
		 * The <code>ControlAligner</code> encapsulated by this
		 * <code>Wrapper</code>.
		 */
		private final ControlAligner controlAligner;

		/**
		 * Creates a new <code>ControlAlignerWrapper</code> that encapsulates
		 * the given <code>ControlAligner</code>.
		 *
		 * @param controlAligner The <code>ControlAligner</code> to be
		 * encapsulated by this <code>Wrapper</code>
		 */
		private ControlAlignerWrapper(ControlAligner controlAligner) {
			super();
			this.controlAligner = controlAligner;
			this.cachedSize     = new Point(controlAligner.maximumWidth, 0);
		}

		/*
		 * (non-Javadoc)
		 */
		public void addListener(Listener listener) {
			controlAligner.addListener(listener);
		}

		/*
		 * (non-Javadoc)
		 */
		public Point cachedSize() {
			cachedSize.x = controlAligner.maximumWidth;
			return cachedSize;
		}

		/*
		 * (non-Javadoc)
		 */
		public Point calculateSize() {

			Point size = new Point(controlAligner.calculateWidth(false), 0);

			if (size.x != SWT.DEFAULT) {
				cachedSize.x = size.x;
			}
			else {
				cachedSize.x = 0;
			}

			if (size.y != SWT.DEFAULT) {
				cachedSize.y = size.y;
			}
			else {
				cachedSize.y = 0;
			}

			return size;
		}

		/*
		 * (non-Javadoc)
		 */
		public boolean locked() {
			return controlAligner.locked;
		}

		/*
		 * (non-Javadoc)
		 */
		public void removeListener(Listener listener) {
			controlAligner.removeListener(listener);
		}

		/*
		 * (non-Javadoc)
		 */
		public void setSize(Point size) {

			if (size == DEFAULT_SIZE) {
				controlAligner.maximumWidth = 0;
			}
			else if (controlAligner.maximumWidth != size.x) {
				controlAligner.maximumWidth = size.x;
				controlAligner.updateWrapperSize(true);
			}
		}

		/*
		 * (non-Javadoc)
		 */
		public Object source() {
			return controlAligner;
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		public String toString() {

			StringBuffer sb = new StringBuffer();
			sb.append("Cached size=");
			sb.append(cachedSize);
			sb.append(", ControlAligner=");
			sb.append(controlAligner);
			return StringTools.buildToStringFor(this, sb);
		}
	}

	/**
	 * This <code>Wrapper</code> encapsulates a {@link Control}.
	 */
	private class ControlWrapper implements Wrapper {
		/**
		 * The cached size, which is control's size.
		 */
		private Point cachedSize;

		/**
		 * The control to be encapsulated by this <code>Wrapper</code>.
		 */
		private final Control control;

		/**
		 * Creates a new <code>controlWrapper</code> that encapsulates the given
		 * control.
		 *
		 * @param control The control to be encapsulated by this <code>Wrapper</code>
		 */
		private ControlWrapper(Control control) {
			super();

			this.control    = control;
			this.cachedSize = new Point(0, 0);
		}

		/*
		 * (non-Javadoc)
		 */
		public void addListener(Listener listener) {

			for (int eventType : EVENT_TYPES) {
				control.addListener(eventType, listener);
			}
		}

		/*
		 * (non-Javadoc)
		 */
		public Point cachedSize() {
			return cachedSize;
		}

		/*
		 * (non-Javadoc)
		 */
		public Point calculateSize() {

			cachedSize = control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);

			// Update right away the control's GridData
			GridData gridData = (GridData) control.getLayoutData();

			if (gridData == null) {
				gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
				control.setLayoutData(gridData);
			}

			gridData.widthHint  = cachedSize.x;
			gridData.heightHint = cachedSize.y;

			// Make sure the size is not -1
			if (cachedSize.x == SWT.DEFAULT) {
				cachedSize.x = 0;
			}

			if (cachedSize.y == SWT.DEFAULT) {
				cachedSize.y = 0;
			}

			return cachedSize;
		}

		/*
		 * (non-Javadoc)
		 */
		public boolean locked() {
			return false;
		}

		/*
		 * (non-Javadoc)
		 */
		public void removeListener(Listener listener) {

			for (int eventType : EVENT_TYPES) {
				control.removeListener(eventType, listener);
			}
		}

		/*
		 * (non-Javadoc)
		 */
		public void setSize(Point size) {

			if (control.isDisposed()) {
				return;
			}

			// Update the GridData with the new size
			GridData gridData = (GridData) control.getLayoutData();

			if (gridData == null) {
				gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
				control.setLayoutData(gridData);
			}

			gridData.widthHint  = size.x;
			gridData.heightHint = size.y;

			// Force the control to be resized, and tell its parent to layout
			// its widgets
			if (size.x > 0) {
//				locked = true;
//				try  {
////					control.getParent().layout(new Control[] { control });
//					control.getParent().layout(true);
//				}
//				finally {
//					locked = false;
//				}
				Rectangle bounds = control.getBounds();

				// Only update the control's width if it's
				// different from the current size
				if (bounds.width != size.x) {
					locked = true;

					try {
//						control.setBounds(bounds.x, bounds.y, size.x, size.y);
						control.getParent().layout(true);
					}
					finally
					{
						locked = false;
					}
				}
			}
		}

		/*
		 * (non-Javadoc)
		 */
		public Control source() {
			return control;
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		public String toString() {

			StringBuffer sb = new StringBuffer();
			sb.append("Cached size=");
			sb.append(cachedSize);
			sb.append(", Control=");
			sb.append(control);
			return StringTools.buildToStringFor(this, sb);
		}
	}

	/**
	 * The listener added to each of the control that is notified in order to
	 * revalidate the preferred size.
	 */
	private class ListenerHandler implements Listener {
		public void handleEvent(Event event) {
			ControlAligner.this.revalidate(event);
		}
	}

	/**
	 * This <code>Wrapper</code> helps to encapsulate heterogeneous objects and
	 * apply the same behavior on them.
	 */
	private interface Wrapper {
	   /**
		 * Adds the given <code>Listener</code> to wrapped object in order to
		 * receive notification when its property changed.
		 *
		 * @param listener The <code>Listener</code> to be added
		 */
		void addListener(Listener listener);

		/**
		 * Returns the cached size of the encapsulated source.
		 *
		 * @return A non-<code>null</code> <code>Point</code> where the x is the
		 * width and the y is the height of the widget
		 */
		Point cachedSize();

		/**
		 * Calculates the preferred size the wrapped object would take by itself.
		 *
		 * @return The calculated size
		 */
		Point calculateSize();

		/**
		 * Prevents infinite recursion when recalculating the preferred width.
		 * This happens in an hierarchy of <code>ControlAligner</code>s.
		 *
		 * @return <code>true</code> to prevent this <code>Wrapper</code> from
		 * being invalidated; otherwise <code>false</code>
		 */
		boolean locked();

		/**
		 * Removes the given <code>Listener</code>.
		 *
		 * @param listener The <code>Listener</code> to be removed
		 */
		void removeListener(Listener listener);

		/**
		 * Sets the size on the encapsulated source.
		 *
		 * @param size The new size
		 */
		void setSize(Point size);

		/**
		 * Returns the encapsulated object.
		 *
		 * @return The object that is been wrapped
		 */
		Object source();
	}
}