#import "SWGDate.h"
#import "SWGWeatherDescription.h"

@implementation SWGWeatherDescription

-(id)_id: (NSNumber*) _id
    main: (NSString*) main
    description: (NSString*) description
    icon: (NSString*) icon
{
  __id = _id;
  _main = main;
  _desc = description;
  _icon = icon;
  return self;
}

-(id) initWithValues:(NSDictionary*)dict
{
    self = [super init];
    if(self) {
        __id = dict[@"id"]; 
        _main = dict[@"main"]; 
        _desc = dict[@"description"];
        _icon = dict[@"icon"]; 
        

    }
    return self;
}

-(NSDictionary*) asDictionary {
    NSMutableDictionary* dict = [[NSMutableDictionary alloc] init];
    if(__id != nil) dict[@"id"] = __id ;
        if(_main != nil) dict[@"main"] = _main ;
        if(_desc != nil) dict[@"description"] = _desc ;
        if(_icon != nil) dict[@"icon"] = _icon ;
        NSDictionary* output = [dict copy];
    return output;
}

@end

