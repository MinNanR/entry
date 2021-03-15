package site.minnan.entry.userinterface.fascade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.entry.domain.aggregate.Traveler;

@Api(tags = "模型")
@RestController
@RequestMapping("/entry/model")
public class ModelController {

    @ApiOperation("旅客实体")
    @PostMapping("traveler")
    @ResponseBody
    public Traveler traveler(){
        return null;
    }

}
