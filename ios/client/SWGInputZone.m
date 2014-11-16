#import "SWGDate.h"
#import "SWGInputZone.h"

@implementation SWGInputZone

-(id)name: (NSString*) name
    logicalPosition: (NSNumber*) logicalPosition
    position: (NSNumber*) position
    inputDeviceId: (NSString*) inputDeviceId
    outputDeviceId: (NSString*) outputDeviceId
{
  _name = name;
  _logicalPosition = logicalPosition;
  _position = position;
  _inputDeviceId = inputDeviceId;
  _outputDeviceId = outputDeviceId;
  return self;
}

-(id) initWithValues:(NSDictionary*)dict
{
    self = [super init];
    if(self) {
        _name = dict[@"name"]; 
        _logicalPosition = dict[@"logicalPosition"]; 
        _position = dict[@"position"]; 
        _inputDeviceId = dict[@"inputDeviceId"]; 
        _outputDeviceId = dict[@"outputDeviceId"]; 
        

    }
    return self;
}

-(NSDictionary*) asDictionary {
    NSMutableDictionary* dict = [[NSMutableDictionary alloc] init];
    if(_name != nil) dict[@"name"] = _name ;
        if(_logicalPosition != nil) dict[@"logicalPosition"] = _logicalPosition ;
        if(_position != nil) dict[@"position"] = _position ;
        if(_inputDeviceId != nil) dict[@"inputDeviceId"] = _inputDeviceId ;
        if(_outputDeviceId != nil) dict[@"outputDeviceId"] = _outputDeviceId ;
        NSDictionary* output = [dict copy];
    return output;
}

@end

