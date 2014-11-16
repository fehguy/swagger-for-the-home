#import "SWGDate.h"
#import "SWGDeviceState.h"

@implementation SWGDeviceState

-(id)deviceId: (NSNumber*) deviceId
    state: (NSNumber*) state
{
  _deviceId = deviceId;
  _state = state;
  return self;
}

-(id) initWithValues:(NSDictionary*)dict
{
    self = [super init];
    if(self) {
        _deviceId = dict[@"deviceId"]; 
        _state = dict[@"state"]; 
        

    }
    return self;
}

-(NSDictionary*) asDictionary {
    NSMutableDictionary* dict = [[NSMutableDictionary alloc] init];
    if(_deviceId != nil) dict[@"deviceId"] = _deviceId ;
        if(_state != nil) dict[@"state"] = _state ;
        NSDictionary* output = [dict copy];
    return output;
}

@end

