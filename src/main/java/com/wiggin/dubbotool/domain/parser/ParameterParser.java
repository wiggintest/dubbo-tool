package com.wiggin.dubbotool.domain.parser;

import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiTypesUtil;
import com.wiggin.dubbotool.domain.model.ClazzInfoDTO;
import com.wiggin.dubbotool.domain.model.DubboToolContext;
import com.wiggin.dubbotool.domain.model.ParameterInfoDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description: 参数解析
 * @author: wiggin
 * @date: 2024-09-22 10:51
 **/
public class ParameterParser extends AbstractParser {
    public ParameterParser(DubboToolContext dubboToolContext) {
        super(dubboToolContext);
    }

    @Override
    public void doParser(ClazzInfoDTO clazzInfoDTO) {
        PsiParameterList psiParameterList = dubboToolContext.getPsiParameterList();
        PsiParameter[] parameters = psiParameterList.getParameters();
        List<ParameterInfoDO> parameterInfoDOS = new ArrayList<>();
        for (PsiParameter parameter : parameters) {
            ParameterInfoDO parameterInfoDO = new ParameterInfoDO();
            parameterInfoDOS.add(parameterInfoDO);
            PsiType psiType = parameter.getType();
            parameterInfoDO.setPsiType(psiType);
            parameterInfoDO.setParameterType(getTypeWithoutGenerics(psiType));
            parameterInfoDO.setParameterName(parameter.getName());
            Object value = getValue(psiType);
            if (Objects.nonNull(value)) {
                parameterInfoDO.setValue(value);
                continue;
            }
            parameterInfoDO.setPsiClass(PsiTypesUtil.getPsiClass(psiType));
        }
        clazzInfoDTO.setParameterInfoDOList(parameterInfoDOS);
    }
}
