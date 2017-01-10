/**
 * ---------------------------------
 * Copyright (c) 2016
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package deadbolt2.common.security;

import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.models.Subject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import models.common.database.User;
import play.mvc.Http;
import play.mvc.Result;

/**
 * <p>An implementation of {@link DeadboltHandler} that handles authorization
 * for the entire RESOLVE WebIDE application.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class WebIDEDeadboltHandler extends AbstractDeadboltHandler {

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This creates the {@link DeadboltHandler} that handles authorization
     * for the entire RESOLVE WebIDE application.</p>
     *
     * @param ecProvider the execution context
     */
    public WebIDEDeadboltHandler(ExecutionContextProvider ecProvider) {
        super(ecProvider);
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Gets the current {@link Subject}, e.g. the current user.</p>
     *
     * @param context the HTTP context
     *
     * @return A {@link User} if the user is logged in, {@code null} otherwise.
     */
    @Override
    public final CompletionStage<Optional<? extends Subject>> getSubject(final Http.Context context) {
        // Retrieve the current user (if logged in)
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(
                User.findByEmail(context.session().get("connected"))),
                (Executor) executionContextProvider.get());
    }

    /**
     * <p>Gets the canonical name of the handler. Defaults to the class name.</p>
     *
     * @return whatever the implementor considers the canonical name of the handler to be.
     */
    @Override
    public final String handlerName() {
        return "RESOLVEWebIDE DeadboltHandler";
    }

    /**
     * <p>Invoked when an access failure is detected on <i>controllerClassName</i>.</p>
     *
     * @param context the HTTP context
     * @param content the content type hint. This can be used to return a response
     *                in the appropriate content type, e.g. JSON.
     *
     * @return the action result
     */
    @Override
    public final CompletionStage<Result> onAuthFailure(Http.Context context,
            Optional<String> content) {
        // TODO: Change this!
        return super.onAuthFailure(context, content);
    }

}