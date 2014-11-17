#import <Foundation/Foundation.h>
#import "SWGObject.h"


@interface SWGWeatherDescription : SWGObject

@property(nonatomic) NSNumber* _id;  

@property(nonatomic) NSString* main;  

@property(nonatomic) NSString* desc;

@property(nonatomic) NSString* icon;  

- (id) _id: (NSNumber*) _id
     main: (NSString*) main
     description: (NSString*) description
     icon: (NSString*) icon;

- (id) initWithValues: (NSDictionary*)dict;
- (NSDictionary*) asDictionary;


@end

