package com.maoyou.cas.authentication.webflow;

import com.maoyou.cas.authentication.credential.CustomCredential;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义web流程（添加新的表单项）
 */
public class CustomWebflowConfigurer extends AbstractCasWebflowConfigurer {


    public CustomWebflowConfigurer(FlowBuilderServices flowBuilderServices,
                                   FlowDefinitionRegistry flowDefinitionRegistry,
                                   ApplicationContext applicationContext,
                                   CasConfigurationProperties casProperties) {
        super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
    }

    @Override
    protected void doInitialize() {
        final Flow flow = super.getLoginFlow();
        bindCredential(flow);
    }

    /**
     * 绑定自定义的Credential信息
     *
     * @param flow
     */
    protected void bindCredential(Flow flow) {

        // 重写绑定自定义credential
        createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL, CustomCredential.class);

        // 登录页绑定新参数
        final ViewState state = (ViewState) flow.getState(CasWebflowConstants.STATE_ID_VIEW_LOGIN_FORM);
        final BinderConfiguration cfg = getViewStateBinderConfiguration(state);
        // 由于用户名以及密码已经绑定，所以只需对新加系统参数绑定即可
        // 字段名，转换器，是否必须字段
//        cfg.addBinding(new BinderConfiguration.Binding("email", null, true));
//        cfg.addBinding(new BinderConfiguration.Binding("telephone", null, true));
        // 注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意
        // 自定义异常信息，最后一个参数改为false
        cfg.addBinding(new BinderConfiguration.Binding("capcha", null, false));


        /**
         * 这里主要更改了两个地方，将必填字段更改为false，让我们手动去判断，
         * 其次添加Webflow的流程，先将原来的action备份一下，然后删除掉，再将需要的action添加进去，同时将备份的action还原。
         */
        final ActionState actionState = (ActionState) flow.getState(CasWebflowConstants.STATE_ID_REAL_SUBMIT);
        final List<Action> currentActions = new ArrayList<>();
        actionState.getActionList().forEach(currentActions::add);
        currentActions.forEach(a -> actionState.getActionList().remove(a));

        actionState.getActionList().add(createEvaluateAction("validateLoginAction"));
        currentActions.forEach(a -> actionState.getActionList().add(a));

//        actionState.getTransitionSet().add(createTransition("emailError", CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM));
//        actionState.getTransitionSet().add(createTransition("telephoneError", CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM));
        // 注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意注意
        // 自定义异常信息，captcha.required要在这里，ValidateLoginAction,messages_zh_CN.properties要保持一致
        actionState.getTransitionSet().add(createTransition("captcha.required", CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM));


    }
}
