/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.clustering.service.concurrent;

/**
 * Allows safe invocation of tasks that require resources not available after {@link #close(Runnable)} to block a service from stopping.
 * @author Paul Ferraro
 */
public interface ServiceExecutor {
    /**
     * Executes the specified task, but only if the service was not already closed.
     * If service is already closed, the task is not run.
     * @param task a task to execute
     */
    void execute(Runnable executeTask);

    /**
     * Closes the service, executing the specified task, first waiting for any concurrent executions to complete.
     * The specified task will only execute once, irrespective on subsequent {@link #close(Runnable)} invocations.
     * @param task a task which closes the service
     */
    void close(Runnable closeTask);
}
