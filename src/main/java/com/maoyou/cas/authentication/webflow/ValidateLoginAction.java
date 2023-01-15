package com.maoyou.cas.authentication.webflow;

import com.maoyou.cas.authentication.credential.CustomCredential;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * @author anumbrella
 */
public class ValidateLoginAction extends AbstractAction {

    private static final String CAPTCHA_CODE = "captcha.required";

    private static final String EMAIL_CODE = "emailError";

    private static final String TELEPHONE_CODE = "telephoneError";

    /**
     * 是否开启验证码
     *
     * @return
     */
    private boolean isEnable() {
        return true;
    }

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        CustomCredential credential = (CustomCredential) WebUtils.getCredential(context);

        System.out.println("excute");

        //系统信息不为空才检测校验码
        if (credential instanceof CustomCredential) {

            String email = credential.getEmail();
            String telephone = credential.getTelephone();
            String capcha = credential.getCapcha();

            if ("".equals(capcha) || capcha == null) {
                return getError(context, CAPTCHA_CODE);
            }

//            if (email.equals("") || email == null) {
//                return getError(context, EMAIL_CODE);
//            }

//            if (telephone.equals("") || telephone == null) {
//                return getError(context, TELEPHONE_CODE);
//            }
        }
        return null;
    }

    /**
     * 跳转到错误页
     *
     * @param requestContext
     * @return
     */
    private Event getError(final RequestContext requestContext, String CODE) {
        final MessageContext messageContext = requestContext.getMessageContext();
        messageContext.addMessage(new MessageBuilder().error().code(CODE).build());
        return getEventFactorySupport().event(this, CODE);
    }
}
